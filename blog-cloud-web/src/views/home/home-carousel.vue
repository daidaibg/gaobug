<script setup lang='ts'>
import {computed} from "vue"
import { useUserStore } from '@/store'
const userStore = useUserStore()

const getUserData=computed(()=>{
    return userStore.getUserData
})

</script>

<template>
    <el-carousel height="100px" :interval="6000">
        <el-carousel-item>
            <div class="carousel_item flex">
                <dl class="today_item flex items-center flex-col justify-center">
                    <dt class="title">今日阅读数</dt>
                    <dd class="today_num">{{ getUserData.readToday }}</dd>
                    <dd class="yestoday">
                        <span>昨日数据</span>
                        <i class="not-italic">{{ getUserData.readYesterday }}</i>
                    </dd>
                </dl>
                <dl class="today_item flex items-center flex-col justify-center">
                    <dt class="title">今日新增赞数</dt>
                    <dd class="today_num">{{ getUserData.likeToday }}</dd>
                    <dd class="yestoday flex">
                        <span>昨日数据</span>
                        <i class="not-italic">{{ getUserData.likeYesterday }}</i>
                    </dd>
                </dl>

            </div>
        </el-carousel-item>
        <el-carousel-item>
            <div class="tips   carousel_item flex flex-col justify-center">
                <p> <strong>搞bug</strong>
                    祝您欢乐生活每一天！</p>

                <p class="mt-1">What you are you do not see, what you see is your shadow.</p>
            </div>
        </el-carousel-item>
    </el-carousel>
</template>

<style scoped lang='scss'>
.carousel_item {
    width: 100%;
    height: 100%;
    background-color: var(--yh-bg-color-secondarycontainer);
    border-radius: 4px;

    .today_item {
        width: 50%;
        text-align: center;


        .title {
            font-size: 14px;
            color: var(--yh-text-color-placeholder);
        }

        .today_num {
            font-size: 18px;
            font-weight: 900;
            padding: 4px 0;
            color: var(--yh-text-color-primary);

        }

        .yestoday {
            font-size: 14px;

            span {
                color: var(--dd-color-secondary-brand)
            }

            i {
                color: var(--yh-text-color-primary);
                margin-left: 4px;

            }
        }
    }


}

.tips {
    font-size: 14px;
    color: var(--yh-text-color-primary);
    text-align: center;
    height: 100%;
}

::v-deep(.el-carousel__indicators) {
    display: none;
}

::v-deep(.el-carousel__arrow) {
    width: 24px;
    height: 24px;

    &.el-carousel__arrow--left {
        left: 6px;
    }

    &.el-carousel__arrow--right {
        right: 6px;
    }
}
</style>