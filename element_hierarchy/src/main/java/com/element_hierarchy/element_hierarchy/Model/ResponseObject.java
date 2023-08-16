package com.element_hierarchy.element_hierarchy.Model;

import java.util.Set;

public class ResponseObject {
    public String status;
    public String message;
    public Set<ADM_REF_UI> data;

    public ResponseObject() {
    }

    public ResponseObject(String status, String message, Set<ADM_REF_UI> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
