package com.ey.xray.cloud.business.uploadAttachment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface UploadAttachment {

    public void uploadAttachmentToJIRAIssue(URL jiraUrl, List<File> filesList, String jiraKey, String xAtlassianToken) throws IOException;

}
