#include "hal.h"

enum State {
    NOANIMATION,
    ANIMATION,
    STOPPED_ANIMATION
};
unsigned int leds_num[] = {GPIO_PIN_3, GPIO_PIN_4, GPIO_PIN_5, GPIO_PIN_6, GPIO_PIN_8, GPIO_PIN_9, GPIO_PIN_10, GPIO_PIN_11, GPIO_PIN_12};
unsigned int sw_num[] = {GPIO_PIN_4, GPIO_PIN_8, GPIO_PIN_10, GPIO_PIN_12};
int delay = 500;

int reset_leds()
{
            for (int i = 0; i <= 8; i++)
            {
                HAL_GPIO_WritePin(GPIOD, leds_num[i], GPIO_PIN_RESET);
            }
            return 0;
}

int check_code() 
{
                GPIO_PinState sw1 = HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_4);
                GPIO_PinState sw2 = HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_8);
                GPIO_PinState sw3 = HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_10);
                GPIO_PinState sw4 = HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_12);
                if (sw1 == 0 && sw2 == 1 && sw3 == 0 && sw4 == 1){
                    return 1;
                } else {
                    return 0;
                };
}

int check_button()
{
    int button_value = HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15);
    if (button_value == 1)
        return 0;
    else return 1;
}

int restore_animation(int animation_step)
{
                for (int i = 0; i <= animation_step; i++) {
                    HAL_GPIO_WritePin(GPIOD, leds_num[i], GPIO_PIN_SET);
                };
                return 0;
}

int umain()
{
    int animation_step = 0;
    int animation_move = 0; //0 - ascending, 1 - descending
    int stopped_animation_flag = 0;
    State state = NOANIMATION;
    while (1)
    {
        if (check_code() == 1) {
            if (state == NOANIMATION) {
                reset_leds();
            }
            if (state == STOPPED_ANIMATION) {
                if (check_button() == 0) {
                    continue;
                } else {
                    stopped_animation_flag = 0;
                    HAL_Delay(delay);
                }
            }
            state = ANIMATION;
            restore_animation(animation_step);
            
        } else {
            if (state == ANIMATION || state == STOPPED_ANIMATION) {
                reset_leds();
            }
            state = NOANIMATION;
        };
        switch (state)
        {
        case NOANIMATION:
        {
            //Yellow LED
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
            for (int i = 0; i < 4; i++)
            {
                GPIO_PinState pin_state = HAL_GPIO_ReadPin(GPIOE, sw_num[i]);
                HAL_GPIO_WritePin(GPIOD, leds_num[i], pin_state);
            };
            break;
        };
        case ANIMATION:
        {
            if (stopped_animation_flag == 1) {
                
                //Red LED
                HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
                HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
                HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
                state = STOPPED_ANIMATION;
                break;
            }
            int interrupted_flag = 0;
            //Green LED
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
            HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
            HAL_Delay(delay);
            if (animation_move == 0)
            {
                for (; animation_step <= 8; animation_step++) {
                    if (check_code() == 0)  {
                        interrupted_flag = 1;
                        break;
                    };
                    if (check_button() == 1) {
                        interrupted_flag = 1;
                        state = STOPPED_ANIMATION;
                        stopped_animation_flag = 1;
                        //RED LED
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
                        HAL_Delay(delay);
                        break;
                    }
                    HAL_GPIO_WritePin(GPIOD, leds_num[animation_step], GPIO_PIN_SET);
                    HAL_Delay(delay);
                };
                if(interrupted_flag == 1){
                    break;
                };
                animation_step--;
                animation_move = 1;
            };
            if (animation_move == 1) {
                for (; animation_step > 0; animation_step--) {
                    if (check_code() == 0)  {
                        interrupted_flag = 1;
                        break;
                    };
                    if (check_button() == 1) {
                        interrupted_flag = 1;
                        state = STOPPED_ANIMATION;
                        stopped_animation_flag = 1;
                        //RED LED
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
                        HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
                        HAL_Delay(delay);
                        break;
                    }
                    HAL_GPIO_WritePin(GPIOD, leds_num[animation_step], GPIO_PIN_RESET);
                    HAL_Delay(delay);
                };
                if(interrupted_flag == 1){
                    break;
                };
                animation_step++;
                animation_move = 0;
           };
           break;
        };
        default:
            break;
        }
    }
    return 0;
}
