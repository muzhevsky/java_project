package org.muzhevsky.resource.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFile {
    private String format;
    private byte[] content;

    public MyFile(){

    }
}
