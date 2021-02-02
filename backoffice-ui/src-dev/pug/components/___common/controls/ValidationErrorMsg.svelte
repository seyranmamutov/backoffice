script.
	import {Utils} from "../../../service/Utils";
	export let fieldName; 
	export let fieldValidationErrors; 
	$: errorMessage = Utils.getErrorByField(fieldName, fieldValidationErrors, false);

|{#if errorMessage}
.fs12.cr.lh12.mt4.tal.h1.mb12 {errorMessage}
|{/if}