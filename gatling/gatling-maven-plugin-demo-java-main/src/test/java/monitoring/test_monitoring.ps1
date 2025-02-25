$logFile = "C:\repository\Masters-Thesis-Blog\gatling\gatling-maven-plugin-demo-java-main\src\test\java\monitoring\gatling_results.csv"
$testName = "GraphQlSimulation500"  # Stała nazwa testu – zmień ręcznie przed uruchomieniem

# Token autoryzacyjny (zmień na swój!)
$token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiI5OWU2NzAwYy01OTNlLTQ5N2MtOGI2YS05ZjAwYjUzZTA1ZmYiLCJleHAiOjE3NDA0NDcwODQsIm5iZiI6MTc0MDQ0MzQ4NH0.YTrFD6fRJPWDgIgqvWjjH8IqZiv9QgHVNQ6ci2LJl_KcMRb0Ggg8lB-etFiBlPoa_t_2UKju-sq1qgq6xQS4YA9nN8eE12IBAHhKlX9ppnMTITfrodRunJUZFZfPoZdEoRtvzXJHnxWWqo0gzgu5t7-7V9FW6h2QIh_52-yU8gxz-0nQdtKnv2XGo0zk50-69lYaxnvrLneIlALtxCv2stJI_JJMqbfaud_CiNWxPXEqQUWyBz8MHIGqYxW_jmD6djZ5r4q_17c2N6d2gkNSmiZA03vXSF8ftb29nj-KbawAir8vU1zm2Mr8dM_eTxzwx8ZPCvzSVRJJireVsdbvYw"
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
    "Accept" = "application/json"
}

# **Dodanie nazwy testu do pliku CSV**
"$testName" | Out-File -FilePath $logFile -Append -Encoding utf8
"Monitoring started..." | Out-File -FilePath $logFile -Append -Encoding utf8

# Nagłówek CSV (tylko dla pierwszego wiersza)
"Timestamp;CPU (%);RAM (MB);Network Sent (KB);Network Received (KB);REST Response Time (ms);REST Status;GraphQL Response Time (ms);GraphQL Status" | Out-File -FilePath $logFile -Append -Encoding utf8

# Monitoring działa do momentu naciśnięcia Ctrl + C
try {
    while ($true) {
        $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
        $cpu = [math]::Round((Get-Counter "\Processor(_Total)\% Processor Time").CounterSamples.CookedValue, 2)
        $ram = [math]::Round((Get-Counter "\Memory\Available MBytes").CounterSamples.CookedValue, 2)

        # Pobranie statystyk sieciowych
        $interfaceName = (Get-NetAdapter | Where-Object { $_.Status -eq "Up" }).Name
        if ($null -eq $interfaceName) {
            $netSent = 0
            $netRecv = 0
        } else {
            $netBefore = Get-NetAdapterStatistics -Name $interfaceName
            Start-Sleep -Milliseconds 500
            $netAfter = Get-NetAdapterStatistics -Name $interfaceName

            $netSent = [math]::Round(($netAfter.SentBytes[0] - $netBefore.SentBytes[0]) / 1024, 2)
            $netRecv = [math]::Round(($netAfter.ReceivedBytes[0] - $netBefore.ReceivedBytes[0]) / 1024, 2)
        }

        # **Testowanie REST API**
        $startTimeRest = Get-Date
        try {
            $responseRest = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/users?page=0&size=100" -Method Get -Headers $headers -ErrorAction Stop
            $restStatus = "OK"
        } catch {
            $restStatus = "Failed"
            Write-Host "REST API Error: $_" -ForegroundColor Red
        }
        $endTimeRest = Get-Date
        $restResponseTime = ($endTimeRest - $startTimeRest).TotalMilliseconds

        # **Testowanie GraphQL API**
        $startTimeGraphQL = Get-Date
        $graphQLBody = @{
            query = "query { listUsers(page: 0, size: 100) { content { id name } } }"
        } | ConvertTo-Json -Depth 10
        
        try {
            $responseGraphQL = Invoke-RestMethod -Uri "http://localhost:8080/graphql" -Method Post -Headers $headers -Body $graphQLBody -ErrorAction Stop
            $graphQLStatus = "OK"
        } catch {
            $graphQLStatus = "Failed"
            Write-Host "GraphQL API Error: $_" -ForegroundColor Red
        }
        $endTimeGraphQL = Get-Date
        $graphQLResponseTime = ($endTimeGraphQL - $startTimeGraphQL).TotalMilliseconds

        # **Dopisanie wyników do pliku CSV**
        "$timestamp;$cpu;$ram;$netSent;$netRecv;$restResponseTime;$restStatus;$graphQLResponseTime;$graphQLStatus" | Out-File -FilePath $logFile -Append -Encoding utf8

        # Pasek postępu
        Write-Progress -Activity "Monitoring system" -Status "Running... Press Ctrl + C to stop."

        Start-Sleep -Seconds 2
    }
}
catch {
    "Monitoring stopped manually." | Out-File -FilePath $logFile -Append -Encoding utf8
}
