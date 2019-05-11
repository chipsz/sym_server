package net.symbiosis.persistence.entity.complex_type.log;

import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.util.Date;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
public class sym_integration_log extends sym_entity<sym_integration_log> {
    @Column(nullable = false)
    private Date outgoing_request_time;
    @Column
    private Date incoming_response_time;
    @ManyToOne
    @JoinColumn(name = "response_code_id")
    private sym_response_code response_code;
    @Column(nullable = false, length = 2048)
    private String outgoing_request;
    @Column(length = 2048)
    private String incoming_response;

    public sym_integration_log() {}

    public sym_integration_log(Date outgoing_request_time, Date incoming_response_time, sym_response_code response_code, String outgoing_request, String incoming_response) {
        this.outgoing_request_time = outgoing_request_time;
        this.incoming_response_time = incoming_response_time;
        this.response_code = response_code;
        this.outgoing_request = outgoing_request;
        this.incoming_response = incoming_response;
    }

    public Date getOutgoing_request_time() { return outgoing_request_time; }

    public sym_integration_log setOutgoing_request_time(Date outgoing_request_time) { this.outgoing_request_time = outgoing_request_time; return this; }

    public Date getIncoming_response_time() { return incoming_response_time; }

    public sym_integration_log setIncoming_response_time(Date incoming_response_time) { this.incoming_response_time = incoming_response_time; return this; }

    public sym_response_code getResponse_code() { return response_code; }

    public sym_integration_log setResponse_code(sym_response_code response_code) { this.response_code = response_code; return this; }

    public String getOutgoing_request() { return outgoing_request; }

    public sym_integration_log setOutgoing_request(String outgoing_request) { this.outgoing_request = outgoing_request; return this; }

    public String getIncoming_response() { return incoming_response; }

    public sym_integration_log setIncoming_response(String incoming_response) { this.incoming_response = incoming_response; return this; }
}
