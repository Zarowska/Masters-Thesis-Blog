# Check CPU information
$cpu = Get-CimInstance Win32_Processor | Select-Object Name, NumberOfCores, NumberOfLogicalProcessors

# Check RAM (GB)
$ram = Get-CimInstance Win32_ComputerSystem | Select-Object TotalPhysicalMemory
$ramGB = "{0:N2} GB" -f ($ram.TotalPhysicalMemory / 1GB)

# Check disk type (SSD/HDD)
$disks = Get-PhysicalDisk | Select-Object DeviceID, MediaType, Size | Format-Table -AutoSize

# Check operating system version
$os = Get-ComputerInfo | Select-Object WindowsProductName, WindowsVersion, OsArchitecture

# Check PowerShell version
$psVersion = $PSVersionTable.PSVersion.ToString()

# Check Gatling version (from pom.xml if available)
$gatlingVersion = "Not found"
if (Test-Path "./pom.xml") {
    $pomContent = Get-Content "./pom.xml" -Raw
    if ($pomContent -match "<artifactId>gatling-maven-plugin-demo-java</artifactId>\s*<version>(.+?)</version>") {
        $gatlingVersion = $matches[1]
    }
}

# Check Node.js version and frameworks (Express & Apollo Server)
$nodeVersion = "Not found"
$expressVersion = "Not found"
$apolloVersion = "Not found"

if (Get-Command node -ErrorAction SilentlyContinue) {
    $nodeVersion = node -v
    $expressVersion = npm list express -g | Select-String "express"
    $apolloVersion = npm list apollo-server -g | Select-String "apollo-server"
}

# Output results to console
$separator = "================================================================"
Write-Host $separator
Write-Host "CPU: $($cpu.Name) | Cores: $($cpu.NumberOfCores) | Threads: $($cpu.NumberOfLogicalProcessors)"
Write-Host "RAM: $ramGB"
Write-Host "Disk types:" 
$disks
Write-Host "Operating System: $($os.WindowsProductName) | Version: $($os.WindowsVersion) | Architecture: $($os.OsArchitecture)"
Write-Host "PowerShell Version: $psVersion"
Write-Host "Gatling Version (from pom.xml): $gatlingVersion"
Write-Host "Node.js Version: $nodeVersion"
Write-Host "Express Version: $expressVersion"
Write-Host "Apollo Server Version: $apolloVersion"
Write-Host $separator
