$libDir = 'C:\Users\Bel\Desktop\PROYECTO-FINAL-SALAS-AGUAYO-MIRTHA\SistemaAsistenciaPlanillas-Salas Mirtha\SistemaAsistenciaPlanillas\lib'
New-Item -ItemType Directory -Force -Path $libDir

$urls = @(
    'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar',
    'https://repo1.maven.org/maven2/com/toedter/jcalendar/1.4/jcalendar-1.4.jar',
    'https://repo1.maven.org/maven2/org/apache/poi/poi/5.2.3/poi-5.2.3.jar',
    'https://repo1.maven.org/maven2/org/apache/poi/poi-ooxml/5.2.3/poi-ooxml-5.2.3.jar',
    'https://repo1.maven.org/maven2/org/apache/poi/poi-ooxml-schemas/4.1.2/poi-ooxml-schemas-4.1.2.jar',
    'https://repo1.maven.org/maven2/org/apache/xmlbeans/xmlbeans/5.1.1/xmlbeans-5.1.1.jar',
    'https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar',
    'https://repo1.maven.org/maven2/org/apache/commons/commons-compress/1.21/commons-compress-1.21.jar',
    'https://repo1.maven.org/maven2/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar'
)

foreach ($url in $urls) {
    $name = [System.IO.Path]::GetFileName($url)
    $dest = Join-Path $libDir $name
    Write-Host "Descargando $name ..."
    Invoke-WebRequest -Uri $url -OutFile $dest
}
Write-Host "Todas las librerias descargadas en $libDir"
