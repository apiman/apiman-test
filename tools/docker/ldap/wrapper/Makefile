CC=gcc
CFLAGS=-std=c99 -Wall

all: wrapper

wrapper: main.o
	$(CC) -o wrapper main.o

main.o: main.c
	$(CC) $(CFLAGS) -c main.c

clean:
	rm wrapper *.o

install: wrapper
	cp wrapper /usr/bin/wrapper

remove:
	rm /usr/bin/wrapper

.PHONY: all clean

