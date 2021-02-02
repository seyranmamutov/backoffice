
<script>
  let klass ;
  export {klass as class}
  export let bgImage = false; 
  
</script>{#if bgImage}
<div class="p20 p24-L w100p oya bgsc bgrnr {klass}" style="background-image: url(static/img/backgrounds/corner.png); background-position: right top;">
  <div class="pr">
    <slot></slot>
  </div>
</div>{:else}
<div class="p20 p24-L w100p bl blci6 oya {klass}">
  <div class="pr">
    <slot></slot>
  </div>
</div>{/if}