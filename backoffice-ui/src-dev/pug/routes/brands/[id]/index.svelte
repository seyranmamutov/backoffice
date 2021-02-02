script.
	import {stores} from '@sapper/app';
	import {NetUtils} from "../../../service/NetUtils";
	import {onMount} from 'svelte';
	import {goto} from '@sapper/app';
	import {Utils} from "../../../service/Utils";
	import Loading from '../../../components/___common/Loading.svelte';
	import {buildUrl} from "../../../service/UrlUtils";
	import ValidationErrorMsg from '../../../components/___common/controls/ValidationErrorMsg.svelte';



	const {page} = stores();
	let id = $page.params.id;
	let isNew = id == 'new';

	let brand = {};
	let aso = Utils.getActionStateObject();

	if (!isNew) {
		NetUtils.get({
			url: buildUrl('api/brands/' + id),
			success: function (data) {
				brand = data;
			}
		});
	}

	function save() {
		NetUtils.request({
			url: buildUrl('api/brands' + (isNew ? "" : "/" + id)),
			params: {
				name: brand.name
			},
			success: function (data) {
				goto(buildUrl('brands'));

			},
			failure: function (data) {
				var error = '';
				try {
					error = error + data.status + '; ' + data.statusText
				} catch (e) {
				}
				alert("Server error: " + error);
			},
			stateChangeCallback: function (newState) {
				aso = newState;
			},
			aso: aso

		}, isNew ? "POST" : "PUT");
	}

.fs20.fw7.cdi.lh12.mb24 {id== 'new'? 'Adding Form': 'Editing Form'}
div.pr
	Loading(actionInProgress=`{aso.actionInProgress}`)

	.bs.br7.p32.bgcw.maw720
		.gy24
			div
				input.input(placeholder='Brand name' bind:value=`{brand.name}`)
				ValidationErrorMsg(fieldName='name' fieldValidationErrors=`{aso.fieldValidationErrors}`)

			div
				.df.jcfe.gx24.aic
					div
						a.button.cb56(href='brands') Cancel
					div
						a.button.bgcb.px24.h40(href='javascript:void(0)' on:click=`{save}`)  Save
