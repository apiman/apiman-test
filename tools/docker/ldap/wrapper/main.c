#define _GNU_SOURCE
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

static char binary_name[] = "/opt/apacheds-2.0.0_M20/bin/apacheds";

static char instance_name[] = "default";

/**
 * @brief executes execl on binary_name with selected comand
 * line arguments
 *
 * @param command apacheDS command to run -- could be start stop console
 * @param additional_arg instance of apacheDS
 *
 * @return This function should never return a value, if it does, the execl funcion
 * failed 
 */
static int exec_prog(char *command, char *additional_arg); 

/**
 * This program wrappes apacheDS daemon, so it could be stopped by
 * SIGTERM signal, which is sent by docker daemon on stop action.
 *
 * Program itself starts ApacheDS daemon as it's child and then sleeps
 * until SIGTERM or SIGINT signal is raised.
 * Then it evokes ApacheDS stop command and waits until daemon is properly
 * stopped and ends itself.
 */
int main(int argc, char *argv[])
{
	char *instance_to_start = NULL;

	if (argc > 2) {
		fprintf(stderr, "Illegal amount of arguments\n");
		return EXIT_FAILURE;
	}

	instance_to_start = (argv[1] == NULL) ? instance_name : argv[1];
	
	int child = fork();

	if (child == 0) {
		exec_prog("console", instance_to_start);
		perror("execl");
		return EXIT_FAILURE;
	}

	int sig_no = 0;
	sigset_t mask;
	
	sigemptyset(&mask);
	sigaddset(&mask, SIGTERM);
	sigaddset(&mask, SIGINT);

	if (sigprocmask(SIG_BLOCK, &mask, NULL) != 0) {
		perror("Masking signal");
		return EXIT_FAILURE;
	}
	
	while(1) {
		if (sigwait(&mask, &sig_no) != 0) {
			perror("sigwait");
			break;
		}
		if (sig_no == SIGTERM || sig_no == SIGINT) {
			break;
		}
	}

	child = fork();

	if (child == 0) {
		fprintf(stderr, "Stopping instance\n");
		exec_prog("stop", instance_name);
		return EXIT_FAILURE;
	}

	while (waitpid(-1, NULL, 0) == 0);

	return EXIT_SUCCESS;
}

static int exec_prog(char *command, char *additional_arg) 
{
	execl(binary_name, binary_name, command, additional_arg, NULL);
	perror("execl: ");
	return EXIT_FAILURE;
}
