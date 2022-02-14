package org.ygaros.usdtopln.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuery {

    private String nameQuery;

    private String dateQuery;

    private Sort nameOrderBy;

    private Sort dateOrderBy;

    /**
     * ignoring boolean values above if TRUE
     */
    private boolean ignoreOrderBy;

}
