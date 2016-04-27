Add-Type -AssemblyName System.IO.Compression.FileSystem

#=====================================================================
#
#   Usage: ./internet_explorer.ps1 </path/to/driver/bin>
#
#   Install latest ie driver as "/path/to/driver/bin"
#   Current folder as default value
#=====================================================================

# Download driver
 $clnt = new-object System.Net.WebClient
 $url = "http://selenium-release.storage.googleapis.com/2.53/IEDriverServer_Win32_2.53.0.zip"
 $file = $PSScriptRoot + "/internet_explorer_driver.zip"
 $clnt.DownloadFile($url, $file)

  if(!$args[0]){
    $path = $PSScriptRoot
  }else{
    $path = $args[0]
  }

# Unzip the file to current or given folder
 [System.IO.Compression.ZipFile]::ExtractToDirectory($file, $path)

  Remove-Item $file