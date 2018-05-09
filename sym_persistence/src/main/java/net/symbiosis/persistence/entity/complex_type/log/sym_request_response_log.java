package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_event_type;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */

@Entity
public class sym_request_response_log extends sym_entity<sym_request_response_log> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id")
    private sym_channel channel;
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_type_id")
    private sym_event_type event_type;
    @ManyToOne
    @JoinColumn(name = "system_user_id")
    private sym_user system_user;
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private sym_auth_user auth_user;
    @ManyToOne
    @JoinColumn(name = "response_code_id")
    private sym_response_code response_code;
    @Basic
    @Column(nullable = false)
    private Date incoming_request_time;
    @Basic
    @Column
    private Date outgoing_response_time;
    @Basic
    @Column(nullable = false, length = 2048)
    private String incoming_request;
    @Basic
    @Column(length = 2048)
    private String outgoing_response;

    public sym_request_response_log() {
    }

    public sym_request_response_log(sym_channel channel,
                                    sym_event_type event_type, String incoming_request) {
        this.channel = channel;
        this.event_type = event_type;
        this.incoming_request = incoming_request;
        this.incoming_request_time = new Date();
    }

    public sym_request_response_log(sym_channel channel, String incoming_request) {
        this(channel, null, incoming_request);
    }

    public sym_request_response_log(sym_channel channel, sym_event_type event_type,
                                    sym_user system_user, sym_auth_user auth_user, sym_response_code response_code,
                                    Date incoming_request_time, Date outgoing_response_time, String incoming_request, String outgoing_response) {
        this.channel = channel;
        this.event_type = event_type;
        this.system_user = system_user;
        this.auth_user = auth_user;
        if (system_user == null && auth_user != null) {
            this.system_user = auth_user.getUser();
        }
        this.response_code = response_code;
        this.incoming_request_time = incoming_request_time;
        this.outgoing_response_time = outgoing_response_time;
        this.incoming_request = incoming_request;
        this.outgoing_response = outgoing_response;
    }

    public sym_channel getChannel() {
        return channel;
    }

    public void setChannel(sym_channel channel) {
        this.channel = channel;
    }

    public sym_event_type getEvent_type() {
        return event_type;
    }

    public void setEvent_type(sym_event_type event_type) {
        this.event_type = event_type;
    }

    public sym_auth_user getAuth_user() {
        return auth_user;
    }

    public void setAuth_user(sym_auth_user auth_user) {
        this.auth_user = auth_user;
    }

    public sym_user getSystem_user() {
        return system_user;
    }

    public void setSystem_user(sym_user system_user) {
        this.system_user = system_user;
    }

    public sym_response_code getResponse_code() {
        return response_code;
    }

    public sym_request_response_log setResponse_code(sym_response_code response_code) {
        this.response_code = response_code;
        return this;
    }

    public Date getIncoming_request_time() {
        return incoming_request_time;
    }

    public void setIncoming_request_time(Date incoming_request_time) {
        this.incoming_request_time = incoming_request_time;
    }

    public Date getOutgoing_response_time() {
        return outgoing_response_time;
    }

    public sym_request_response_log setOutgoing_response_time(Date outgoing_response_time) {
        this.outgoing_response_time = outgoing_response_time;
        return this;
    }

    public String getIncoming_request() {
        return incoming_request;
    }

    public void setIncoming_request(String incoming_request) {
        this.incoming_request = incoming_request;
    }

    public String getOutgoing_response() {
        return outgoing_response;
    }

    public sym_request_response_log setOutgoing_response(String outgoing_response) {
        this.outgoing_response = outgoing_response;
        return this;
    }
}
