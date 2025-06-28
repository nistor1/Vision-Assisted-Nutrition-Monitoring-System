import React from 'react';
import { Descriptions, Divider } from 'antd';
import type { DailyStatisticResponse } from '../../types/MealEntities';

interface DailyStatisticViewProps {
  stat: DailyStatisticResponse;
  goals: Record<string, number>;
  onGoalsChange?: (updatedGoals: Record<string, number>) => void;
  editable?: boolean;
}



const getColor = (value: number, goal?: number): string | undefined => {
  if (goal === undefined || goal === null || goal === 0) return undefined;
  const lower = goal - goal * 0.2;
  const upper = goal + goal * 0.2;
  return value >= lower && value <= upper ? 'green' : 'red';
};

const getUnit = (section: string): string => {
  switch (section) {
    case 'Minerals':
    case 'Vitamins':
      return 'mg';
    case 'Proximates':
    case 'Carbohydrates':
    default:
      return 'g';
  }
};

const renderSection = (
  title: string,
  data: Record<string, number | string | null | undefined>,
  goals: Record<string, number>
) => {
  const unit = getUnit(title);

  return (
    <>
      <Divider orientation="left">{title}</Divider>
      <Descriptions bordered column={2} size="small">
        {Object.entries(data).map(([key, value]) => {
          const goal = goals[key];
          const hasGoal = goal !== undefined && goal !== null && goal !== 0;

          return typeof value === 'number' ? (
            <Descriptions.Item key={key} label={key} labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
              <span style={{ color: getColor(value, goal) }}>
                {value.toFixed(2)}
              </span>
              {hasGoal && (
                <> / {goal} {unit}</>
              )}
            </Descriptions.Item>
          ) : null;
        })}
      </Descriptions>
    </>
  );
};

const DailyStatisticView: React.FC<DailyStatisticViewProps> = ({ stat, goals }) => {
  return (
    <>
      <Descriptions title="General" bordered column={2} size="small" labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        <Descriptions.Item label="Date">{stat.date}</Descriptions.Item>

        <Descriptions.Item label="Calories">
          <span style={{ color: getColor(stat.totalCalories, goals.totalCalories) }}>
            {stat.totalCalories.toFixed(2)}
          </span>
          {goals.totalCalories ? <> / {goals.totalCalories} kcal</> : null}
        </Descriptions.Item>

        <Descriptions.Item label="Proteins">
          <span style={{ color: getColor(stat.totalProteins, goals.totalProteins) }}>
            {stat.totalProteins.toFixed(2)}
          </span>
          {goals.totalProteins ? <> / {goals.totalProteins} g</> : null}
        </Descriptions.Item>

        <Descriptions.Item label="Fats">
          <span style={{ color: getColor(stat.totalFats, goals.totalFats) }}>
            {stat.totalFats.toFixed(2)}
          </span>
          {goals.totalFats ? <> / {goals.totalFats} g</> : null}
        </Descriptions.Item>

        <Descriptions.Item label="Carbs">
          <span style={{ color: getColor(stat.totalCarbs, goals.totalCarbs) }}>
            {stat.totalCarbs.toFixed(2)}
          </span>
          {goals.totalCarbs ? <> / {goals.totalCarbs} g</> : null}
        </Descriptions.Item>

        <Descriptions.Item label="Sugars">
          <span style={{ color: getColor(stat.totalSugars, goals.totalSugars) }}>
            {stat.totalSugars.toFixed(2)}
          </span>
          {goals.totalSugars ? <> / {goals.totalSugars} g</> : null}
        </Descriptions.Item>
      </Descriptions>

      {renderSection('Proximates', stat.proximates as unknown as Record<string, number>, goals)}
      {renderSection('Carbohydrates', stat.carbohydrates as unknown as Record<string, number>, goals)}
      {renderSection('Minerals', stat.minerals as unknown as Record<string, number>, goals)}
      {renderSection('Vitamins', stat.vitamins as unknown as Record<string, number>, goals)}
    </>
  );
};

export default DailyStatisticView;
