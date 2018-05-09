package net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_event_type;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */

@Entity
public class bt_request_response_log extends bt_entity<bt_request_response_log> {
    @ManyToOne(optional = false)
	@JoinColumn(name = "channel_id")
	private bt_channel channel;
	@ManyToOne(optional = false)
	@JoinColumn(name = "event_type_id")
	private bt_event_type event_type;
	@ManyToOne @JoinColumn(name = "system_user_id")
	private bt_user system_user;
	@ManyToOne @JoinColumn(name = "auth_user_id")
	private bt_auth_user auth_user;
	@ManyToOne @JoinColumn(name = "response_code_id")
	private bt_response_code response_code;
	@Basic @Column(nullable = false)
	private Date incoming_request_time;
	@Basic @Column
	private Date outgoing_response_time;
	@Basic @Column(nullable = false, length = 2048)
	private String incoming_request;
	@Basic @Column(length = 2048)
	private String outgoing_response;

	public bt_request_response_log() {}

	public bt_request_response_log(bt_channel channel,
              bt_event_type event_type, String incoming_request) {
	    this.channel = channel;
	    this.event_type = event_type;
	    this.incoming_request = incoming_request;
        this.incoming_request_time = new Date();
	}

	public bt_request_response_log(bt_channel channel, String incoming_request) {
	    this(channel, null, incoming_request);
	}

    public bt_request_response_log(bt_channel channel, bt_event_type event_type,
            bt_user system_user, bt_auth_user auth_user, bt_response_code response_code,
            Date incoming_request_time, Date outgoing_response_time, String incoming_request, String outgoing_response) {
        this.channel = channel;
        this.event_type = event_type;
        this.system_user = system_user;
        this.auth_user = auth_user;
        if (system_user == null && auth_user != null) { this.system_user = auth_user.getUser(); }
        this.response_code = response_code;
        this.incoming_request_time = incoming_request_time;
        this.outgoing_response_time = outgoing_response_time;
        this.incoming_request = incoming_request;
        this.outgoing_response = outgoing_response;
    }

    public bt_channel getChannel() { return channel; }

	public void setChannel(bt_channel channel) { this.channel = channel; }

	public bt_event_type getEvent_type() { return event_type; }

	public void setEvent_type(bt_event_type event_type) { this.event_type = event_type; }

	public bt_auth_user getAuth_user() { return auth_user; }

	public void setAuth_user(bt_auth_user auth_user) { this.auth_user = auth_user; }

	public bt_user getSystem_user() { return system_user; }

	public void setSystem_user(bt_user system_user) { this.system_user = system_user; }

	public bt_response_code getResponse_code() {
		return response_code;
	}

	public bt_request_response_log setResponse_code(bt_response_code response_code) {
		this.response_code = response_code;
        return this;
	}

	public Date getIncoming_request_time() { return incoming_request_time; }

	public void setIncoming_request_time(Date incoming_request_time) {
		this.incoming_request_time = incoming_request_time;
	}

	public Date getOutgoing_response_time() { return outgoing_response_time; }

	public bt_request_response_log setOutgoing_response_time(Date outgoing_response_time) {
		this.outgoing_response_time = outgoing_response_time;
		return this;
	}

	public String getIncoming_request() { return incoming_request; }

	public void setIncoming_request(String incoming_request) { this.incoming_request = incoming_request; }

	public String getOutgoing_response() { return outgoing_response; }

	public bt_request_response_log setOutgoing_response(String outgoing_response) {
	    this.outgoing_response = outgoing_response;
	    return this;
	}
}
