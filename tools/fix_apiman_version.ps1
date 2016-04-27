#=======================================================================
#
#   Usage: ./fix_apiman_version.ps1 <apiman version>
#
#   This script is used to fix the value of ${version.apiman} property
#   in ${TEST_SOURCES}/pom.xml. If no version is provided, then then
#   value is extracted from ${APIMAN_SOURCES}/pom.xml
#=======================================================================

$test_sources=iex "Split-Path -Path $PSScriptRoot -Parent"

if(!$args[0]){
    $apiman_sources=Invoke-Command {(Split-Path -Path $test_sources -Parent).Trim() + "\apiman"}
    $apiman_version=Invoke-Command {((Get-content $apiman_sources\pom.xml) `
                                    | Where-Object {$_ -match "<version>\w[^<]+"}`
                                    | Select-Object -first 1 `
                                    | Out-String).Trim() -replace "<\/*\w+>", "" }
}else{
    $apiman_version=$args[0]
}

Invoke-Command {(Get-content $test_sources\pom.xml) `
| foreach-object {$_ -replace "<version.apiman>.*<\/version.apiman>", "<version.apiman>$apiman_version</version.apiman>"} `
| set-content $test_sources\pom.xml}