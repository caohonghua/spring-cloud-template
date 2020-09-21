package org.caohh.server.web.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Resource implements Serializable {
    private String text;
    private String value;
    private List<Resource> children;
}
