package com.ey.xray.cloud.business.uploadAttachment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import com.ey.xray.cloud.infrastructure.buildAPIRequests.MakeAPICall;


public class UploadAttachmentImpl implements UploadAttachment {
    public void uploadAttachmentToJIRAIssue(URL jiraUrl, List<File> filesList, String jiraKey, String xAtlassianToken) throws IOException {
        MakeAPICall makeAPICall = new MakeAPICall();
        makeAPICall.attachFiles(jiraUrl, filesList, jiraKey, xAtlassianToken);

    }
}
