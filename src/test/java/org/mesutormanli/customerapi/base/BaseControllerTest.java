package org.mesutormanli.customerapi.base;

import com.google.gson.Gson;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class BaseControllerTest {

    protected static final MediaType contentType = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    protected final String json(Object o) {
        return new Gson().toJson(o);
    }

}
