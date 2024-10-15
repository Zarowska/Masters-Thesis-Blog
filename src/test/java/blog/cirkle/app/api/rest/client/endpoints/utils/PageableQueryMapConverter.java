package blog.cirkle.app.api.rest.client.endpoints.utils;

import blog.cirkle.app.api.rest.client.model.Pageable;
import java.util.HashMap;
import java.util.Map;

public class PageableQueryMapConverter {

	public static Map<String, String> toMap(Pageable pageable) {
		Map<String, String> map = new HashMap<>();
		if (pageable != null) {
			map.put("page", String.valueOf(pageable.getPage()));
			map.put("size", String.valueOf(pageable.getSize()));
			// Add sorting details if needed
			if (pageable.getSort() != null) {
				// TODO
				// pageable.getSort().forEach(order -> {
				// map.put("sort", order.getProperty() + "," + order.getDirection());
				// });
			}
		}
		return map;
	}
}