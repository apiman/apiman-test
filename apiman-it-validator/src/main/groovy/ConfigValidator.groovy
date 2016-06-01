import groovy.json.JsonSlurper

/**
 * @author opontes
 */

def assertValidMetadata = { json ->
    json?.Metadata?.apimanVersion == version
}

def assertValidVersion = { json ->
    json?.version == version
}

def assertValidPlugins = { json ->
    json?.plugins?.every { it?.version == version }
}

def assertValidRepository = { json ->
    json?.repository?.url == repository
}

def setUpCliBuilder() {
    def cli = new CliBuilder(usage: 'ConfigValidator -d <amg_dir> -r <repository_url>')
    cli.with {
        d(longOpt: 'amg_dir', 'AMG directory', args: 1, required: true)
        r(longOpt: 'repository_url', 'Maven repository url', args: 1, required: true)
        v(longOpt: 'version', 'Apiman version', args: 1, required: false)
    }
    return cli.parse(args)
}

def MAPPER = [
        "/apiman/data/basic-settings.apiman.json" : [
                        assertValidMetadata
                ],
        "/standalone/data/bootstrap/amg-config.json" : [
                        assertValidMetadata
                ],
        "/apiman/bootstrap/apiman-default-config.json" : [
                        assertValidMetadata
                ],
        "/standalone/configuration/amg-plugin-registry.json" : [
                        assertValidVersion,
                        assertValidPlugins,
                        assertValidRepository
                ]
]

def messages = []
def o = setUpCliBuilder()
if (!o) return
def amg_home = o.d
version = o.v
repository = o.r

MAPPER.each { path, asserts ->
    def json = new JsonSlurper().parse(new File(amg_home + path))
    def isValid = asserts.every { validator ->  validator(json)  }
    if (!isValid) messages.add("Possible error is in file: ${path}")
}
(messages.empty) ? println("Files are OK.") : println(messages)