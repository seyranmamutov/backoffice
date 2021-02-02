
<script>
  import SidebarContainer from '../../components/___common/SidebarContainer.svelte';
  import ContentContainer from '../../components/___common/ContentContainer.svelte';
  import {buildUrl} from "../../service/UrlUtils";
  import {goto} from '@sapper/app';
  
  import DataTable from '../../components/___common/table/DataTable.svelte';
  import Icon from '../../components/___common/Icon.svelte';
  import TableLink from '../../components/___common/TableLink.svelte';
  import {Utils} from '../../service/Utils';
  
  
  let columns = [
  	{title: "id", name: "id", orderable: true},
  	{title: "Brand Name", name: "name", orderable: true, render: function (raw) {
  			return '<a class="tdu" href="brands/' + raw.id + '">' + raw.name + '</a>';
  		}
  	},
  	{title: "Inventory:", name: "inventorySum", orderable: true},
  	{title: "Actions", name: "name", orderable: false, render: function (raw) {
  			return '<a class="tdu" href="brands/' + raw.id + '/delete">Delete</a>';
  		}
  	}
  
  ];
  
  function addBrand() {
  	goto(buildUrl('brands/new'));
  }
  
</script>
<div class="r aic gy8">
  <div class="i">
    <div class="fs24 fw7 cdi lh12">Brands</div>
  </div>
  <div class="i0">
    <div class="r aic gx20">
      <button class="button bgcb h40 px24 mb24" id="moreBtn" type="button" on:click="{addBrand}">Add a new brand	</button>
    </div>
  </div>
</div>
<div class="mb20"></div>
<DataTable columns="{columns}" url="{buildUrl('api/brands')}"></DataTable>