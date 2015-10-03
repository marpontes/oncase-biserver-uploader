package com.oncase.biserver.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/{plugin}/api/upload")
public class UploaderREST {
	
	public static final String DIRECTORY = "../temp/";
	
	@POST
	@Path("/send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@Context UriInfo info,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail,

		@FormDataParam("endpointPath") String endpointPath,
		@FormDataParam("queryParameters") String queryParameters
			) throws URISyntaxException {
		
		
		String uploadedFileLocation = DIRECTORY + fileDetail.getFileName();

		String filePath = new File(uploadedFileLocation).getAbsolutePath();
		
		try {
			
			writeToFile(uploadedInputStream, uploadedFileLocation);
			
		} catch (IOException e) {
			System.out.println("[File Uploader] Error uploading file: "+filePath);
			e.printStackTrace();
		}
 
		URI pentahoBaseUrl = info.getBaseUri().resolve("../");
		endpointPath = pentahoBaseUrl + endpointPath;
		endpointPath += "?paramfileUrl="+filePath+queryParameters;

		return Response.temporaryRedirect(new URI(endpointPath)).build();
	}
	
	
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) throws IOException {
		File dir = new File(DIRECTORY);
		dir.mkdirs();
		File file = new File(uploadedFileLocation);
		file.createNewFile();
		OutputStream out = new FileOutputStream(file);
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

}