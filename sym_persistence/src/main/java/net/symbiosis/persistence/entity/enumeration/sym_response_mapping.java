package net.symbiosis.persistence.entity.enumeration;

import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
@Table(indexes = {@Index(name = "index_system_id",  columnList="system_id", unique = true)})
public class sym_response_mapping extends sym_entity<sym_response_mapping> {

    @Column(nullable = false)
    public String system_id;

    @Column(nullable = false)
    public Long response_code_id;

    @ManyToOne
    @JoinColumn(nullable = false)
    public sym_response_code mapped_response_code;

    @Column(nullable = false)
    public String mapped_response_message;

    public sym_response_mapping() {}

    public sym_response_mapping(String system_id, Long response_code_id, sym_response_code mapped_response_code, String mapped_response_message) {
        this.system_id = system_id;
        this.response_code_id = response_code_id;
        this.mapped_response_code = mapped_response_code;
        this.mapped_response_message = mapped_response_message;
    }

    public String getSystem_id() { return system_id; }

    public void setSystem_id(String system_id) { this.system_id = system_id; }

    public Long getResponse_code_id() { return response_code_id; }

    public void setResponse_code_id(Long response_code_id) { this.response_code_id = response_code_id; }

    public sym_response_code getMapped_response_code() { return mapped_response_code; }

    public void setMapped_response_code(sym_response_code mapped_response_code) { this.mapped_response_code = mapped_response_code; }

    public String getMapped_response_message() { return mapped_response_message; }

    public void setMapped_response_message(String mapped_response_message) { this.mapped_response_message = mapped_response_message; }
}
