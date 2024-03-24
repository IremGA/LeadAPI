package org.eaetirk.efd.lead.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eaetirk.efd.lead.constant.LeadAPIConstant;
import org.eaetirk.efd.lead.model.Lead;

import java.util.List;

@ApplicationScoped
public class LeadRepository implements PanacheRepository<Lead> {
    public List<Lead> findLeadByFirstName(String firstName){
       return find(LeadAPIConstant.QUERY_LIST_BY_FIRSTNAME_ORDER_BY_LAST_NAME, firstName.toLowerCase()).stream().toList();
   }

   public List<Lead> findLeadByLastName(String lastName){
        return find(LeadAPIConstant.QUERY_LIST_BY_LASTNAME_ORDER_BY_FIRST_NAME, lastName.toLowerCase()).stream().toList();
    }

    public List<Lead> findLeadByFirstNameAndLastName(String firstName, String lastName){
       return find(LeadAPIConstant.QUERY_LIST_BY_LASTNAME_AND_FIRST_NAME, firstName.toLowerCase(), lastName.toLowerCase()).stream().toList();
    }

    public List<Lead> findLeadContainFirstName(String firstName){
        return list(LeadAPIConstant.QUERY_LIST_CONTAINS_CHAR_IN_FIRSTNAME_ORDER_BY_FIRST_NAME, "%"+firstName.toLowerCase()+"%").stream().toList();

    }

    public List<Lead> findLeadContainLastName(String lastName){
        return list(LeadAPIConstant.QUERY_LIST_CONTAINS_CHAR_IN_LASTNAME_ORDER_BY_LAST_NAME, "%"+lastName.toLowerCase()+"%").stream().toList();

    }

    public List<Lead> findLeadContainFirstORLastName(String firstName, String lastName){
        return list(LeadAPIConstant.QUERY_LIST_CONTAINS_CHAR_IN_LASTNAME_OR_FIRSTNAME_ORDER_BY_LAST_NAME, "%"+firstName.toLowerCase()+"%","%"+lastName.toLowerCase()+"%").stream().toList();

    }
}
