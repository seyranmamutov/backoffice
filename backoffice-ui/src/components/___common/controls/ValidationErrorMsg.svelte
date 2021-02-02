
<script>
  import {Utils} from "../../../service/Utils";
  export let fieldName; 
  export let fieldValidationErrors; 
  $: errorMessage = Utils.getErrorByField(fieldName, fieldValidationErrors, false);
  
</script>{#if errorMessage}
<div class="fs12 cr lh12 mt4 tal h1 mb12">{errorMessage}</div>{/if}