import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.Arrays;
import java.util.List;

public class ResponseUtils {

    public static String getHeader(CloseableHttpResponse response, String headerName) {
        Header[] headers = response.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader = "";

        for(Header header : httpHeaders) {
            if(headerName.equalsIgnoreCase(header.getName())) {
                returnHeader = header.getValue();
            }
        }

        if(returnHeader.isEmpty()) {
            throw new RuntimeException("Didn't find the header: " + headerName);
        }

        return returnHeader;
    }

    public static String getHeaderJava8Way(CloseableHttpResponse response, final String headerName) {
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

        Header matchHeader = httpHeaders.stream()
                                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                                .findFirst().orElseThrow(() -> new RuntimeException("Didn't find the header"));

        return matchHeader.getValue();
    }

    public static boolean headerIsPreset(CloseableHttpResponse response, final String headerName) {
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

        return httpHeaders.stream()
                .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }
}
