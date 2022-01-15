package com.test.authservice.security;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.test.authservice.dto.RequestDto;

@Component
@ConfigurationProperties(prefix="admin-paths")
public class RouteValidator {
	
	private List<RequestDto> paths;

    public List<RequestDto> getPaths() {
        return paths;
    }

    public void setPaths(List<RequestDto> paths) {
        this.paths = paths;
    }

    /**
	 * isAdminPath validate if the information on dto is equals to some admin-paths
	 * @param dto object that contains the information of validation
	 * @return True if dto is on admin-paths
	 */
    public boolean isAdminPath(RequestDto dto) {
        return paths.stream().anyMatch(p ->
                Pattern.matches(p.getUri(), dto.getUri()) && p.getMethod().equals(dto.getMethod()));
    }
}
