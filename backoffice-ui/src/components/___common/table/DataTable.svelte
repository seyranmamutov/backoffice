
<script>
  import {onMount} from 'svelte';
  import {NetUtils} from '../../../service/NetUtils';
  import {Utils} from '../../../service/Utils';
  import Icon from '../Icon.svelte';
  import {createEventDispatcher} from 'svelte';
  import Paginator from "./Paginator.svelte";
  
  const dispatch = createEventDispatcher();
  
  
  // example of columns metadata
  // let columns = [
  //     {title: "ID", name: "id", orderable: false},
  //     {title: "Domain", name: "domain", orderable: false},
  //     {
  //         title: "Price", name: "price", orderable: false, render: function (row) {
  //             return '$ ' + row.price
  //         }
  //     }
  // ]
  
  // ================= INPUT PARAMS =========================
  
  export let columns;
  export let url;
  export let params = {};
  export let batchSelection = false; // if true - checkboxes is rendered to select items for batch operation
  export let hidePaging = false; // do not show paging
  export let idColumnName = "id";
  export let forceDataReloadingCounter = 0; // need to increment (change) counter to trigger (force) data reloading
  export let selectedIds = {};//map of selected checkboxes
  export let displayActionColumn = false;
  export let dataSet; // array  of objects
  
  //TABLE DATA SET
  // there are 2 possible way to render data:
  // 1. specify url and params and data will be loaded from it
  // Response format is: 
  //{
  //      "totalPages": 2,
  //      "content": [
  //        {
  //          "id": 1,
  //          "name": bla.com,
  //			...
  //        }, ...
  //		]
  // 2. directly specify data
  // data format is: 
  // 		[
  //        {
  //          "id": 1,
  //          "name": bla.com,
  //			...
  //        }, ...
  // 		]
  let data = dataSet? {content:dataSet} : {}; // optional if url is specified 
  
  
  // =======================	
  
  
  $: totalPages = data? data.totalPages: 1;
  let aso = Utils.getActionStateObject();
  
  let allSelected = false;
  
  $: syncBatchSelectionCheckbox(selectedIds);
  
  function syncBatchSelectionCheckbox(selectedIds){
  	if (Object.keys(selectedIds).length == 0) {
  		allSelected = false;
  	} 
  } 
  
  let sort = {
  	field: null,
  	asc: false
  }
  
  let page = 1;
  let size = 5;
  
  $: toggleAllSelected(allSelected);
  $: sortParams = getParams(page, size, sort);
  
  $: updateData(sortParams, params, url, forceDataReloadingCounter);
  
  $: sendSelectedChangedEvent(selectedIds);
  
  function sendSelectedChangedEvent(selectedIds) {
  	let selectedRows = []
  	if (data && data.content) {
  		data.content.forEach(function (row) {
  			Object.keys(selectedIds).forEach(function (id) {
  				if (getValue(idColumnName, row) == id && selectedIds[id]) {
  					selectedRows.push(row)
  				}
  			})
  
  		});
  	}
  	dispatch('changedSelectedRows', {
  		selected: selectedRows
  	});
  	
  }
  
  function toggleAllSelected(allSelected) {
  	if (data && data.content) {
  		data.content.forEach(function (row) {
  			selectedIds[getValue(idColumnName, row)] = allSelected;
  		});
  	} 
  }
  
  function getParams(page, size, sort) {
  	let params =  {}
  	params.page = page;
  	params.size = size;
  	if (sort.field != null) {
  		params.sort= sort.field + ":" + (sort.asc? 'asc':'desc')
  	}
  	return params;
  }
  
  function updateData(sortParams, params, url,forceDataReloadingCounter) {
  	if (url) {
  		NetUtils.get({
  			url: url,
  			params: Utils.merge(params, sortParams),
  			success: function (result) {
  				data = result;
  			},
  			stateChangeCallback: function (newState) {
  				aso = newState;
  			},
  			aso: aso
  		});
  	} 
  }
  
  function onEdit(i) {
  	dispatch('edit', {
  		item: i
  	});
  }
  
  function onDelete(i) {
  	dispatch('delete', {
  		item: i
  	});
  }
  
  function onSorted(fieldName) {
  	page = 1;
  	if (fieldName == sort.field) {
  		// the same field - toggle sort direction
  		sort.asc = !sort.asc;
  	} else {
  		// different sort field - set asc = true
  		sort.asc = true;
  	}
  	sort.field = fieldName
  }
  
  function getValue(columnName, obj) {
  	var arr = columnName.split(".");
  	var value = obj;
  	arr.forEach(function (v) {
  		value = value[v];
  	});
  	if (value == null) {
  		value = '';
  	} 
  	return value
  }
  
  
  
</script>
<div class="pr">
  <table class="table">
    <tr class="bgci2">
      <!-- TABLE HEADER-->
      <!-- header columns-->{#each columns as c, i}
      <th class="{c.thClass?c.thClass:''}">
        <div class="df aic">
          <label class="dif aic">{#if batchSelection && i == 0}
            <!-- render Batch selection checkboxes-->
            <input class="checkbox mr12" type="checkbox" bind:checked="{allSelected}">{/if}
            {#if c.orderable}
            <!-- if field is orderable--><a class="dif aic ci" href="javascript:void(0);" on:click="{a => onSorted(c.name)}"><span>{c.title}</span>{#if c.name != sort.field}
              <!-- sorting is not active (up-down arrow)-->
              <Icon class="w8 h10 ml4 cli" n="m-triangle2"></Icon>{:else if sort.asc}
              <!-- sort asc-->
              <Icon class="wh7 ml4 ci" n="m-triangle"></Icon>{:else}
              <!-- sort desc-->
              <Icon class="wh7 ml4 ci tsr180" n="m-triangle"></Icon>{/if}</a>{:else}
            <!-- no sorting for this field-->{c.title}
            {/if}
          </label>
        </div>
      </th>{/each}
      <!-- actions column header-->{#if displayActionColumn}
      <th class="w40">
        <div class="df aic">
          <label class="dif aic">Edit/Remove</label>
        </div>
      </th>{/if}
    </tr>{#if !aso.actionInProgress && data.content && data.content.length > 0}
    <!-- iterage over data-->
    <!-- TABLE CONTENT	-->{#each data.content as e}
    <tr>
      <!-- build rows based on column metadata-->{#each columns as c, i}
      <td>{#if batchSelection && i == 0 }
        <div class="df aic">
          <label class="dif aic">
            <!-- batch selection checkboxes-->
            <input class="checkbox mr12" type="checkbox" bind:checked="{selectedIds[getValue(idColumnName, e)]}">
            <!--span.ml16.lh12  Item checkbox-->{#if c.render}
            <!-- custom column render-->{@html c.render(e)}
            {:else if c.component}
            <svelte:component this="{c.component(e).name}" {...c.component(e).params}></svelte:component>{:else}
            {getValue(c.name, e)}
            {/if}
          </label>
        </div>{:else}
        {#if c.render}
        <!-- custom column render-->{@html c.render(e)}
        {:else if c.component} 
        <svelte:component this="{c.component(e).name}" {...c.component(e).params}></svelte:component>{:else}
        {getValue(c.name, e)}
        {/if}
        {/if}
      </td>{/each}
      {#if displayActionColumn}
      <td>
        <div class="gx16 df aic jcfe">
          <div>
            <button class="button cb56" type="button" on:click="{()=>onEdit(e)}">
              <Icon class="wh16" n="m-edit"></Icon>
            </button>
          </div>
          <div>
            <button class="button cb56" type="submit" on:click="{()=>onDelete(e)}">
              <Icon class="wh16" n="m-close"></Icon>
            </button>
          </div>
        </div>
      </td>{/if}
      <slot></slot>
    </tr>{/each}
    {:else}
    <tr>
      <td colspan="30">
        <div class="df aic jcc cli p24">No records found</div>
      </td>
    </tr>{/if}
  </table>{#if !hidePaging}
  <Paginator bind:pageSize="{size}" totalPages="{totalPages}" bind:currentPage="{page}"></Paginator>{/if}
</div>