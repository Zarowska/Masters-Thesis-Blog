$logFile = "C:\repository\Masters-Thesis-Blog\gatling\gatling-maven-plugin-demo-java-main\src\test\java\monitoring\gatling_results.csv"

Write-Host "Monitoring started..."

# Nagłówek CSV (jeśli plik nie istnieje, dodaj nagłówek)
if (-Not (Test-Path $logFile)) {
    "Timestamp,CPU (%),RAM (MB),Network Sent (KB),Network Received (KB)" | Out-File -FilePath $logFile -Encoding utf8
}

# Ustawienie czasu monitorowania (60 sekund)
$endTime = (Get-Date).AddSeconds(60)

while ((Get-Date) -lt $endTime) {
    # Pobranie danych systemowych
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $cpu = (Get-Counter "\Processor(_Total)\% Processor Time").CounterSamples.CookedValue
    $ram = [math]::Round((Get-Counter "\Memory\Available MBytes").CounterSamples.CookedValue, 2)

    # Pobranie statystyk sieciowych
    $interfaceName = (Get-NetAdapter | Where-Object { $_.Status -eq "Up" }).Name
    if ($null -eq $interfaceName) {
        Write-Host "No active network interface found!" -ForegroundColor Red
        $netSent = 0
        $netRecv = 0
    } else {
        $netBefore = Get-NetAdapterStatistics -Name $interfaceName
        Start-Sleep -Milliseconds 500
        $netAfter = Get-NetAdapterStatistics -Name $interfaceName

        # Pobieramy pierwszą wartość z tablicy [0]
        $netSent = [math]::Round(($netAfter.SentBytes[0] - $netBefore.SentBytes[0]) / 1024, 2)
        $netRecv = [math]::Round(($netAfter.ReceivedBytes[0] - $netBefore.ReceivedBytes[0]) / 1024, 2)
    }

    # Zapis danych do pliku
    "$timestamp,$cpu,$ram,$netSent,$netRecv" | Out-File -FilePath $logFile -Append -Encoding utf8

    # Pasek postępu (bez polskich znaków)
    $remainingTime = [math]::Round(($endTime - (Get-Date)).TotalSeconds)
    Write-Progress -Activity "Monitoring system" -Status "$remainingTime seconds remaining" -PercentComplete (100 - ($remainingTime / 60 * 100))

    # Czekaj 2 sekundy przed kolejnym pomiarem
    Start-Sleep -Seconds 2
}

# Dźwiękowy sygnał zakończenia testu
[console]::beep(1000, 500)
Write-Host "Monitoring finished. Results saved in: $logFile"

