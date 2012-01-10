<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Main">
    <h3>Send POST requests to <span id="api-context">URL</span></h3>
    <a id="send-invalid" href="#">Post Invalid Request</a><br/>
    <a id="send-valid" href="#">Post Valid Request</a>
    <hr/>
    <p id="status-holder">Status</p>

    <script type="text/javascript">
        (function ($) {
            var REST_API_CONTEXT = "<c:url value="/" />";
            var semicolonPos = REST_API_CONTEXT.indexOf(';');
            if (semicolonPos >= 0) {
                REST_API_CONTEXT = REST_API_CONTEXT.substring(0, semicolonPos);
            }
            if (REST_API_CONTEXT[REST_API_CONTEXT.length - 1] == '/') {
                REST_API_CONTEXT = REST_API_CONTEXT.substring(0, REST_API_CONTEXT.length - 1);
            }
            REST_API_CONTEXT = REST_API_CONTEXT + "/rest";
            $("#api-context").text(REST_API_CONTEXT);

            // AJAX wrapper
            function invokeApi(methodType, data, relUrl, okFn) {
                var methodUrl = REST_API_CONTEXT + relUrl;

                if (data != null && !(data instanceof Object)) {
                    window.alert("Data " + data + " expected to be an object");
                }

                $.ajax({
                    "url": methodUrl,
                    "type": methodType,
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "data": data != null ? JSON.stringify(data) : null,
                    "success": function(data, status, xhr) {
                        //queryElem.attr('disabled', '');

                        $("#status-holder").text("Operation " + methodType + " " + methodUrl + " succeeded, " +
                                "data: " + JSON.stringify(data) + ", status: " + status);

                        if (okFn != null) {
                            okFn(data, status, xhr);
                        }
                    },
                    "error": function(xhr, errorText, errorThrown) {
                        //queryElem.attr('disabled', 'disabled');

                        $("#status-holder").text("Error for " +
                                methodType + " " + methodUrl + ", text: '" +
                                errorText + "', description: '" + errorThrown + "'");
                    }
                })
            }

            // handlers
            $("#send-invalid").click(function () {
                invokeApi(
                        "POST",
                        { "origin": "Client JS" },
                        "/hello",
                        null
                );
            });

            $("#send-valid").click(function () {
                invokeApi(
                        "POST",
                        { "origin": "Client JS", "greeting": "Hello from Client" },
                        "/hello",
                        null
                );
            });
        })(jQuery)
    </script>
</tag:pageWrapper>