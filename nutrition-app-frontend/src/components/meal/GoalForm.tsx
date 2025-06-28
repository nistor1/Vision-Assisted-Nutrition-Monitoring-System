import React from 'react';
import { Form, InputNumber, Button, Card, Divider } from 'antd';
import type { GoalsStatistics } from '../../types/MealEntities';

interface GoalFormProps {
  initialValues: GoalsStatistics;
  onSubmit: (values: GoalsStatistics) => void;
  onCancel: () => void;
}

const GoalForm: React.FC<GoalFormProps> = ({ initialValues, onSubmit, onCancel }) => {
  const [form] = Form.useForm();

  const renderSection = (title: string, keys: (keyof GoalsStatistics)[]) => (
    <>
      <Divider orientation="left">{title}</Divider>
      {keys.map((key) => (
        <Form.Item
          key={key}
          label={key}
          name={key}
          rules={[]}
        >
          <InputNumber style={{ width: '100%' }} min={0} step={0.01} />
        </Form.Item>
      ))}
    </>
  );

  return (
    <Card style={{ maxWidth: 800, margin: '0 auto' }}>
      <Form
        form={form}
        layout="vertical"
        initialValues={initialValues}
        onFinish={onSubmit}
      >
        {renderSection('General', [
          'totalCalories', 'totalProteins', 'totalCarbohydrates', 'totalFats', 'totalSugars'
        ])}

        {renderSection('Carbohydrates', [
          'carbohydrate', 'fiber', 'sucrose', 'glucose', 'fructose', 'maltose', 'lactose'
        ])}

        {renderSection('Minerals', [
          'calcium', 'iron', 'magnesium', 'phosphorus', 'potassium',
          'sodium', 'zinc', 'copper', 'manganese'
        ])}

        {renderSection('Proximates', [
          'water', 'energyGeneral', 'energySpecific', 'nitrogen',
          'protein', 'totalLipid', 'ash'
        ])}

        {renderSection('Vitamins', [
          'vitaminA', 'vitaminB1', 'vitaminB2', 'vitaminB3', 'vitaminB5',
          'vitaminB6', 'vitaminB7', 'vitaminB9', 'vitaminB12', 'vitaminC',
          'vitaminD', 'vitaminE', 'vitaminK'
        ])}

        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ marginRight: 8 }}>
            Save
          </Button>
          <Button onClick={onCancel}>Cancel</Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default GoalForm;
