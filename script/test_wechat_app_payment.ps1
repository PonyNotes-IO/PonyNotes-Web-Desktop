param([string]$Maven = 'mvn', [switch]$LocalPostgres)

$ErrorActionPreference = 'Stop'
$paymentRoot = Split-Path $PSScriptRoot -Parent
Push-Location $paymentRoot
try {
    & $Maven -B -q -pl ruoyi-admin -am test-compile dependency:build-classpath '-Dmdep.outputFile=target/wechat-app-check-classpath.txt'
    if ($LASTEXITCODE -ne 0) { throw 'Payment check compilation failed' }
    $paymentCp = (Get-Content 'ruoyi-admin/target/wechat-app-check-classpath.txt' -Raw).Trim()
    $paymentClasses = @(
        'ruoyi-admin/target/test-classes', 'ruoyi-admin/target/classes',
        'ruoyi-system/target/classes', 'ruoyi-common/target/classes',
        'ruoyi-framework/target/classes', 'ruoyi-xmbj-api/target/classes'
    ) | ForEach-Object { Join-Path $paymentRoot $_ }
    $paymentJavaOptions = @('-Dfile.encoding=UTF-8')
    if ($LocalPostgres) { $paymentJavaOptions += '-Dpayment.check.localDatabase=true' }
    & java @paymentJavaOptions -cp (($paymentClasses -join ';') + ';' + $paymentCp) com.ruoyi.web.service.WechatAppPaymentCheck
    if ($LASTEXITCODE -ne 0) { throw 'Payment checks failed' }
} finally {
    Pop-Location
}
