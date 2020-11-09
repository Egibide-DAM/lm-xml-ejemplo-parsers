## Importar el certificado de Izenpe para el JDK

1. Descargar el certificado raíz (`CA Raiz [2F 78 3D 25 52 18 A7 4A 65 39 71 B5 2C A2 9C 45 15 6F E9 19]`) de [Izenpe](https://www.izenpe.eus/web_corporativa/es/descarga-certificados.shtml).

2. Localizar la ruta del JDK:

	macOS
	
	```
	export JAVA_HOME=$(/usr/libexec/java_home)
	```

	Windows

	```
	set JAVA_HOME=C:\Program Files\Java\jdk-11.0.3
	```

	Modificarlo con la ruta exacta dependiendo de la versión instalada.


3. Instalar el certificado en el keystore del JDK:

	macOS

	```
	sudo keytool -import -alias certificadoIzenpe -keystore $JAVA_HOME/lib/security/cacerts -file RAIZ2007_cert_sha256.crt
	```

	Windows (en un CMD con permisos de administrador)

	```
	"%JAVA_HOME%\bin\keytool.exe" -import -alias certificadoIzenpe -keystore "%JAVA_HOME%/lib/security/cacerts" -file RAIZ2007_cert_sha256.crt
	```

	La contraseña del keystore predeterminado es `changeit`.

### Otras tareas

Listar los certificados:

```
keytool -keystore $JAVA_HOME/lib/security/cacerts -list -v
```

Borrar el certificado:

```
sudo keytool -delete -alias certificadoIzenpe -keystore $JAVA_HOME/lib/security/cacerts
```

### Referencias

[How to Import Public Certificates into Java’s Truststore from a Browser | by Avinash Reddy Penugonda | Expedia Group Technology | Medium](https://medium.com/expedia-group-tech/how-to-import-public-certificates-into-javas-truststore-from-a-browser-a35e49a806dc)
