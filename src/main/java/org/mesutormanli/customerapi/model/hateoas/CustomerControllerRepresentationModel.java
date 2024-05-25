package org.mesutormanli.customerapi.model.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public final class CustomerControllerRepresentationModel extends RepresentationModel<CustomerControllerRepresentationModel> {
    public CustomerControllerRepresentationModel(Link initialLink) {
        super(initialLink);
    }
}
