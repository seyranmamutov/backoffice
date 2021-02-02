
<script>
  import Icon from '../Icon.svelte';
  export let pageSize;
  export let totalPages;
  export let currentPage;
  
</script>
<!-- PAGINATOR	-->
<div class="bgci2 px12 py12 bt btcb4">
  <div class="r aic jcsb">
    <div class="i0">
      <div class="df aic gx20">
        <div class="cb56 ml8">View</div>
        <select class="select h28 bdr3" bind:value="{pageSize}" on:change="{v =>  currentPage =1}">
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="15">15</option>
          <option value="50">50</option>
        </select>
      </div>
    </div>
    <div class="i0">
      <div class="df aic gx20">
        <div class="cb56">Page {currentPage} of {totalPages}</div>
        <button class="button wh40 brp bgc9-hf cli ci-hf" on:click="{v => currentPage = currentPage - 1}" disabled="{currentPage <=1}">
          <Icon class="wh14 ml-2" n="m-angle"></Icon>
        </button>
        <button class="button wh40 brp bgc9-hf cli ci-hf" on:click="{v => currentPage = currentPage + 1}" disabled="{currentPage >=totalPages}">
          <Icon class="wh14 tsr180 mr-2" n="m-angle"></Icon>
        </button>
      </div>
    </div>
  </div>
</div>