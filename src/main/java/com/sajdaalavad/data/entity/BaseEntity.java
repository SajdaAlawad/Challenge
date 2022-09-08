package com.sajdaalavad.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//lombok
@Data
@NoArgsConstructor
//super
@MappedSuperclass
//audit
@EntityListeners(AuditingEntityListener.class)

@JsonIgnoreProperties(value = {""},allowGetters = true)
public class BaseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column (name = "id", updatable = false, nullable = false)
   private Long id;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    @Column(name = "update_date")
    @LastModifiedDate
    private String updateDate;
}


//kim ekledi
// kim ne zaman ekledi
//kim güncelledi
//kim ne zaman  güncelledi
//json tarafindan parse edilmesini istedigimiz istemedigimizi dosyalari icin kullaniyoruz