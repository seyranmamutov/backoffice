import {buildUrl} from "./UrlUtils";

export class NetUtils {
	static async get(obj) {
		return NetUtils.request(obj, 'GET');
	}

	static async delete(obj) {
		return NetUtils.request(obj, 'DELETE');
	}

	static async post(obj) {
		return NetUtils.request(obj, 'POST');
	}

	static async put(obj) {
		return  NetUtils.request(obj, 'PUT');
	}
	static async patch(obj) {
		return  NetUtils.request(obj, 'PATCH');
	}
	
	static async request(obj, method) {
		// console.log("REQUEST " + method + " -> " + obj.url);
		updateState(obj, true, null);

		if (obj.start) {
			obj.start();
		}
		var url = obj.url;
		if (method === 'DELETE' || method == 'GET') {
			if (obj.params) {
				if (url.indexOf('?') > 0) {
					url = url + "&"
				} else {
					url = url + '?'
				}
				url = url + buildQueryString(obj.params);
			}
		}

		let res;

		if (method == 'DELETE' || method == 'GET') {
			if (typeof obj.fetch !== 'undefined' ) {
				res = await obj.fetch(url, {'method': method});
			} else {
				res = await fetch(url, {'method': method});
			}
			
		} else {
			if (obj.fetch) {
				res = await obj.fetch(url, {
						'method': method,
						'headers': {
							'Content-Type': 'application/json'
						},
						'body': JSON.stringify(obj.params)
					});
			} else {
				res = await fetch(url, {
						'method': method,
						'headers': {
							'Content-Type': 'application/json'
						},
						'body': JSON.stringify(obj.params)
					});
			}
			
		}

		let json;
		
		if (res.status == 403) {
			window.location.href = buildUrl('');
		} else  if (res.redirected) {
			// is session is expired - go to home page
			window.location.href = res.url;
		} else if (res.ok) {

			updateState(obj, false, null);

			if (method == 'DELETE') {
				if (obj.success) {
                    obj.success();	
				} 
				
			} else {
				try {
					json = await
						res.json();
					if (obj.success) {
						obj.success(json);
					}
				} catch (e) {
					// console.log("ERROR:" + e);
                    if (obj.success) {
                        obj.success();
                    }
                }
			   
			}
		} else {
			if (res.status == 400) {
                json = await res.json();
                updateState(obj, false, json.form, json.message);
			} else {
                updateState(obj, false, null, null);
                if (obj.failure) {
                    obj.failure(res);
                }
            }
			


		}

		return {data: json, status: res.status};
	}

}

function buildQueryString(obj) {
	var str = "";
	for (var key in obj) {
		if (str != "") {
			str += "&";
		}
		str += encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]);
	}
	return str;
}

function updateState(obj, actionInProgress, fieldValidationErrors, message) {
	if (obj.aso) {
		obj.aso.message = message;
		obj.aso.fieldValidationErrors = fieldValidationErrors;
		obj.aso.actionInProgress = actionInProgress;
		if (obj.stateChangeCallback) {
			obj.stateChangeCallback(obj.aso);
		}
	}
}