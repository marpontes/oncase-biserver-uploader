# oncase-biserver-uploader
Bean for Pentaho that implements an uploader that redirects the call to a given endpoint passing the file path and more parameters

## Purpose

With sparkl, there's now a lot of plugins being built to Pentaho BI-Server. However, at this point it's still not possible to have upload easily implemented on Pentaho without coding a custom Java class.

With oncase-biserver-uploader, you should simply add our lib and change your `plugin.spring.xml` to be ready for uploading and post-processing your files.

## Enabling

You should only copy our JAR `oncase-biserver-uploader-X.X.X.jar` to your plugin lib folder and then add the following line to your `plugin.spring.xml`, after the `org.pentaho.platform.web.servlet.JAXRSPluginServlet` bean:

```xml
<bean id="YOU_PLUGIN_ID.uploader.api" class="com.oncase.biserver.ws.UploaderREST"/>
```

After this, you just need to restart your bi-server and you should be ready to use

## Using

You can now upload your files and tell the uploader which endpoint you want to call next and what parameters to forward.

The multipart/form-data POST should be sent to `/pentaho/plugin/YOU_PLUGIN_ID/api/upload/send` and this endpoint accepts the parameters:
 * **file** - The file that will be uploaded;
 * **endpointPath** - The path to the endpoint that will be called after
the upload is done. This path is relative to the contextPath (after the
…:8080/pentaho/) ex.: `plugin/tapa/api/uploadedfiletester` ;
 * **queryParameters** - Any queryparameters you’d want to forward to
your endpoint. ex.: `&paramtemplate=tapa-default&paramtableId=123`.

### Examples

There are some example files inside of `resources/` folder.

