
<script>
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
  
</script>
<div class="fs20 fw7 cr lh12 mb24 fw4">Brand deletion</div>{#if aso.message}
<div class="p12 bgcr cw mb12 maw720">{aso.message}</div>{/if}		
<div class="pr">
  <Loading actionInProgress="{aso.actionInProgress}"></Loading>
  <div class="bs br7 p32 bgcw maw720">
    <div class="gy24">
      <div>Are you sure you want to delete brand <b>{brand.name}?</b></div>
      <div>
        <div class="df jcfe gx24 aic">
          <div><a class="button cb56" href="brands">Cancel</a></div>
          <div><a class="button bgcr px24 h40" href="javascript:void(0)" on:click="{deleteBrand}"> Delete</a></div>
        </div>
      </div>
    </div>
  </div>
</div>