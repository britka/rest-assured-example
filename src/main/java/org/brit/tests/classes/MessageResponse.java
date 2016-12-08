package org.brit.tests.classes;

/**
 * Created by sbryt on 9/9/2016.
 */

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MessageResponse {

    private Integer code;
    private String type;
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public MessageResponse() {
    }

    /**
     * @param message
     * @param code
     * @param type
     */
    public MessageResponse(Integer code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    public MessageResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public MessageResponse withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}