export class Utils {
    static getCreatedFilter(days) {
        var currDate = new Date();
        currDate.setDate(currDate.getDate() - days);
        return currDate.toISOString().slice(0, 10);

    }
    
    static merge(obj1, obj2) {
        return {...obj1, ...obj2}
    }
    
    
    // Example of error 
    // [
    //         {
    //           "field": "offer",
    //           "message": "must not be null"
    //         },
    //         {
    //           "field": "buyerEmail",
    //           "message": "must not be blank"
    //         },
    //         {
    //           "field": "buyerName",
    //           "message": "must not be blank"
    //         }
    //       ]
    static getErrorByField(field, errors, hide) {
        if (errors && !hide) {
            let fieldError = errors.filter(function (el) {
                return el.field == field}
            );
            
            if (fieldError && fieldError.length > 0) {
                return fieldError[0].message
            }
        }
        
    }
    
    // object hold state during REST communication to backend
    static getActionStateObject() {
        return {
            actionInProgress: false,
            fieldValidationErrors: null
        }
    }
    
    // build custom error message in structure which correspond to structure of validation error response (400 Bad Request) 
    static validateIsNotEmpty(value, field, message) {
        let result = [];
        if (!value || value.trim() == '') {
            result = [{
                "field": field,
                "message": message
            }];
        } 
        return result;
    }

    
}
