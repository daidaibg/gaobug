<template>
    <div class="card-container-wrapper">
      <div class="card-container" ref="container">
        <Card v-for="(item, index) in cardList" :key="index">
          <img :src="item.image" />
        </Card>
        <Card v-if="!isFirstCard" class="card-clone" :style="{ left: '-100%' }">
          <img :src="cardList[cardList.length - 1].image" />
        </Card>
        <Card class="active-card" :style="{ left: `-${100 * activeIndex}%` }">
          <img :src="cardList[activeIndex].image" />
        </Card>
        <Card v-if="!isLastCard" class="card-clone" :style="{ left: `${100 * (cardList.length + 1)}%` }">
          <img :src="cardList[0].image" />
        </Card>
      </div>
    </div>
  </template>
  
  <script>
  import Card from './card.vue';
  import { reactive, ref, computed, watch ,onMounted,onUnmounted} from 'vue';
  
  export default {
    name: 'CardContainer',
    components: {
      Card,
    },
    props: {
      cardList: {
        type: Array,
        default: () => [],
      },
    },
    setup(props) {
      const container = ref(null);
      const state = reactive({
        activeIndex: 0,
      });
      const isFirstCard = computed(() => state.activeIndex === 0);
      const isLastCard = computed(() => state.activeIndex === props.cardList.length - 1);
      
      const setActiveIndex = (index) => {
        if (index >= props.cardList.length) {
          state.activeIndex = 0;
        } else if (index < 0) {
          state.activeIndex = props.cardList.length - 1;
        } else {
          state.activeIndex = index;
        }
      };
      
      const onTouchEnd = () => {
        let targetIndex = -1;
        const currentLeft = container.value.offsetLeft;
        const maxLeft = 100 * props.cardList.length;
        if (currentLeft < -maxLeft) {
          targetIndex = 0;
        } else if (currentLeft > 0) {
          targetIndex = props.cardList.length - 1;
        } else {
          targetIndex = Math.abs(currentLeft) / 100;
        }
        setActiveIndex(targetIndex);
        container.value.style.transition = 'all 0.3s';
        container.value.style.left = `${-100 * targetIndex}%`;
      };
      
      watch(() => state.activeIndex, (index, oldIndex) => {
        if (index !== oldIndex) {
          container.value.style.transition = 'all 0.3s';
          container.value.style.left = `${-100 * index}%`;
        }
      });
      
      watch(() => props.cardList, (list) => {
        if (list && list.length > 0) {
          // 初始化状态
          setActiveIndex(0);
        }
      });
      
      onMounted(() => {
        container.value.addEventListener('transitionend', onTouchEnd);
      });
      
      onUnmounted(() => {
        container.value.removeEventListener('transitionend', onTouchEnd);
      });
      
      return {
        container,
        activeIndex: state.activeIndex,
        isFirstCard,
        isLastCard,
      };
    },
  };
  </script>