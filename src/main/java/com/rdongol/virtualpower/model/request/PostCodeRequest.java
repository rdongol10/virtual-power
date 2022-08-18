package com.rdongol.virtualpower.model.request;

import java.util.List;

public class PostCodeRequest implements Request {
    private List<String> postCodes;

    public List<String> getPostCodes() {
        return postCodes;
    }

    public void setPostCodes(List<String> postCodes) {
        this.postCodes = postCodes;
    }
}
