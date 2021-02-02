script.
    import {stores} from '@sapper/app';
    import {NetUtils} from "../../../../service/NetUtils";
    import {goto} from '@sapper/app';

    import {onMount} from 'svelte';
    import {Utils} from "../../../../service/Utils";
    import Loading from '../../../../components/___common/Loading.svelte';
    import {buildUrl} from "../../../../service/UrlUtils";
    import ValidationErrorMsg from '../../../../components/___common/controls/ValidationErrorMsg.svelte';

    const {page} = stores();
    let id = $page.params.id;

    let brand = {};
    let aso = Utils.getActionStateObject();

    NetUtils.get({
        url: buildUrl('api/brands/' + id),
        success: function (data) {
            brand = data;
        }
    });

    function deleteBrand() {
        NetUtils.request({
            url: buildUrl('api/brands/' + id ),
            success: function (data) {
                goto(buildUrl('brands'));

            },
            stateChangeCallback: function (newState) {
                aso = newState;
                console.log(aso);
            },
            aso: aso

        }, "DELETE");
    }

.fs20.fw7.cr.lh12.mb24.fw4 Brand deletion
| {#if aso.message}
.p12.bgcr.cw.mb12.maw720 {aso.message}
| {/if}		
div.pr
    Loading(actionInProgress=`{aso.actionInProgress}`)

    .bs.br7.p32.bgcw.maw720
        .gy24
            div Are you sure you want to delete brand 
                b {brand.name}?
            div
                .df.jcfe.gx24.aic
                    div
                        a.button.cb56(href='brands') Cancel
                    div
                        a.button.bgcr.px24.h40(href='javascript:void(0)' on:click=`{deleteBrand}`)  Delete
