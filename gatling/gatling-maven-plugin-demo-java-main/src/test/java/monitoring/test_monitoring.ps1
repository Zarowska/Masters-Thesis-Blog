# Test Configuration
$logFile = "C:\repository\Masters-Thesis-Blog\gatling\gatling-maven-plugin-demo-java-main\src\test\java\monitoring\gatling_results.csv"
#$testName = "RestApiSimulation100"  # Manually change before running the test

# Authorization token (change to your own!)
$token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiI5OWU2NzAwYy01OTNlLTQ5N2MtOGI2YS05ZjAwYjUzZTA1ZmYiLCJleHAiOjE3NDA1MzE1MTYsIm5iZiI6MTc0MDUyNzkxNn0.gk-rLR0NegSnRg2YyVEo2hh5jAKw3LeAmpzSE-hUGaQvNVB4E2Vkl59dsG_Uk37SFEHAqjn9N-NHhlC9PuqxZijczMxodZAHnlhW-dXC2liS6GwSP92VKp3VsDQwEGjHGg0BjjBR0Y1WWwjRR8NusWKkVAqfOpnoaUNwwICckdlxyci2QP6dW9tcAmgaQ7-pWG7xWNBFjH_r_hwYYL6d5HqPFQTbsbDJY-IBupV4wppR2dIYJK9ib0Gc71463eXBiS0_bdur5UtflrbTO9ZAUby_8At4q4uZjZT5ZDWnCb_DKosrBoKP7NNraAyr8Epl2fnbT9O2DouLMiTV0kCQCA"
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
    "Accept" = "application/json"
}

# Adding the test name to the CSV file
"" | Out-File -FilePath $logFile -Append -Encoding utf8    
#"$testName" | Out-File -FilePath $logFile -Append -Encoding utf8
"Timestamp;CPU (%);RAM (MB);Network Sent (KB);Network Received (KB);REST Response Time (ms);REST Status;GraphQL Response Time (ms);GraphQL Status" | Out-File -FilePath $logFile -Append -Encoding utf8

# Adding CSV header (only if the file is empty)
# if ((Get-Content $logFile).Count -eq 3) {
#     "Timestamp;CPU (%);RAM (MB);Network Sent (KB);Network Received (KB);REST Response Time (ms);REST Status;GraphQL Response Time (ms);GraphQL Status" | Out-File -FilePath $logFile -Append -Encoding utf8
# }

# Monitoring - runs until Ctrl + C is pressed
try {
    while ($true) {
        $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
        $cpu = [math]::Round((Get-Counter "\Processor(_Total)\% Processor Time").CounterSamples.CookedValue, 2)
        $ram = [math]::Round((Get-Counter "\Memory\Available MBytes").CounterSamples.CookedValue, 2)

        # Fetching network statistics
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

        # Testing REST API
        $startTimeRest = Get-Date
        try {
            $responseRest = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/users?page=0&size=100" -Method Get -Headers $headers -ErrorAction Stop
			#$apiUrl = "http://localhost:8080/api/v1/users/a14092e3-cf86-4337-b75e-b76df514385f/posts?page=0&size=10"
			#$apiUrl = http://localhost:8080/api/v1/users?page=0&size=100"
            $restStatus = "OK"
        } catch {
            $restStatus = "Failed"
            Write-Host "REST API Error: $_" -ForegroundColor Red
        }
        $endTimeRest = Get-Date
        $restResponseTime = ($endTimeRest - $startTimeRest).TotalMilliseconds

        # Testing GraphQL API
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

        # Appending results to the CSV file
        "$timestamp;$cpu;$ram;$netSent;$netRecv;$restResponseTime;$restStatus;$graphQLResponseTime;$graphQLStatus" | Out-File -FilePath $logFile -Append -Encoding utf8

        # Progress bar
        Write-Progress -Activity "Monitoring system" -Status "Running... Press Ctrl + C to stop."

        Start-Sleep -Seconds 2
    }
}
catch {
    "Monitoring stopped manually." | Out-File -FilePath $logFile -Append -Encoding utf8
}
