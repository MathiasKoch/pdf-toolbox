package com.dong.matko.toolbox.cpw;
import com.microsoft.schemas.sharepoint.soap.GetVersionsResponse;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap;

public class DocumentLibrary {

	public DocumentLibrary() {}

	public static GetVersionsResponse.GetVersionsResult VersionsGetVersions(VersionsSoap port, String FileName) throws Exception
	{
			try {
				GetVersionsResponse.GetVersionsResult Result = port.getVersions(FileName);
				return Result;
			} catch (Exception e) {
				throw new Exception("Error: " + e.toString());
			}
	}
}
