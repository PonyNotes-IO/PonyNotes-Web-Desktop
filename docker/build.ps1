<#
.SYNOPSIS
    构建并保存Docker镜像的PowerShell脚本

.DESCRIPTION
    该脚本接受一个版本号参数，执行以下操作：
    1. 使用docker build构建镜像（标签为xm-web:<版本号>）
    2. 使用docker save将镜像保存为tar文件

.PARAMETER Version
    指定镜像的版本号（如1.4）

.EXAMPLE
    .\build.ps1 -Version 1.4
#>

param (
    [Parameter(Mandatory=$true, HelpMessage="请输入镜像版本号（如1.4）")]
    [string]$Version
)

# 检查是否安装了Docker
try {
    $dockerVersion = docker --version
    Write-Host "检测到Docker: $dockerVersion" -ForegroundColor Green
}
catch {
    Write-Host "错误：未检测到Docker安装。请先安装Docker。" -ForegroundColor Red
    exit 1
}

# 构建Docker镜像
$buildCommand = "docker build -t xm-dashboard:$Version ."
Write-Host "正在构建镜像: $buildCommand" -ForegroundColor Cyan
Invoke-Expression $buildCommand

# 检查上一条命令是否成功
if ($LASTEXITCODE -ne 0) {
    Write-Host "错误：镜像构建失败！" -ForegroundColor Red
    exit $LASTEXITCODE
}

# 保存Docker镜像
$saveCommand = "docker save -o xm-dashboard.tar xm-dashboard:$Version"
Write-Host "正在保存镜像: $saveCommand" -ForegroundColor Cyan
Invoke-Expression $saveCommand

# 检查上一条命令是否成功
if ($LASTEXITCODE -ne 0) {
    Write-Host "错误：镜像保存失败！" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "操作成功完成！" -ForegroundColor Green
Write-Host "镜像已保存为: $(Get-Location)\xm-dashboard.tar" -ForegroundColor Yellow