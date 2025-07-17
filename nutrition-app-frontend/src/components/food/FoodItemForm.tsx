import React, { useEffect } from 'react';
import {
  Form, Input, Button, Card, InputNumber, Select, Switch, Divider
} from 'antd';
import type { FoodItem } from '../../types/FoodEntities';

const { Option } = Select;

interface FoodFormProps {
  initialValues?: Partial<FoodItem>;
  onSubmit: (values: FoodItem) => void;
  onCancel: () => void;
  loading?: boolean;
}

const FoodForm: React.FC<FoodFormProps> = ({ initialValues, onSubmit, onCancel, loading }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (initialValues) {
      form.setFieldsValue(initialValues);
    }
  }, [initialValues, form]);

  const handleFinish = (values: Partial<FoodItem>) => {
    const fullValues: FoodItem = {
      ...(initialValues ?? {}),
      ...values,
      proximates: {
        ...(initialValues?.proximates ?? {}),
        ...(values.proximates ?? {}),
      },
      carbohydrates: {
        ...(initialValues?.carbohydrates ?? {}),
        ...(values.carbohydrates ?? {}),
      },
      minerals: {
        ...(initialValues?.minerals ?? {}),
        ...(values.minerals ?? {}),
      },
      vitamins: {
        ...(initialValues?.vitamins ?? {}),
        ...(values.vitamins ?? {}),
      },
    } as FoodItem;

    onSubmit(fullValues);
  };

  const renderNutritionFields = (prefix: string, fields: string[], unit: string) =>
    fields.map((field) => (
      <Form.Item
        key={field}
        name={[prefix, field]}
        label={field}
        rules={[
          {
            validator: (_, value) =>
              value === null || value === undefined || typeof value === 'number'
                ? Promise.resolve()
                : Promise.reject(new Error(`${field} must be a number`)),
          },
        ]}
      >
        <InputNumber min={0} addonAfter={unit} style={{ width: '100%' }} />
      </Form.Item>

    ));

  const proximatesUnit = Form.useWatch(['proximates', 'unit'], form);
  const carbohydratesUnit = Form.useWatch(['carbohydrates', 'unit'], form);
  const mineralsUnit = Form.useWatch(['minerals', 'unit'], form);
  const vitaminsUnit = Form.useWatch(['vitamins', 'unit'], form);

  return (
    <Card>
      <Form layout="vertical" form={form} onFinish={handleFinish}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2>General Info</h2>
          <Button onClick={onCancel} type="default">
            Cancel
          </Button>
        </div>

        <Form.Item name="foodName" label="Food Name" rules={[{ required: true }]}>
          <Input />
        </Form.Item>

        <Form.Item name="category" label="Category" rules={[{ required: true }]}>
          <Select placeholder="Select category">
            <Option value="Fruit">Fruit</Option>
            <Option value="Vegetable">Vegetable</Option>
            <Option value="Other">Other</Option>
          </Select>
        </Form.Item>

        <Form.Item name="imageUrl" label="Image URL">
          <Input />
        </Form.Item>

        <Form.Item name="servingSize" label="Serving Size" rules={[{ required: true }]}>
          <InputNumber min={0} style={{ width: '100%' }} />
        </Form.Item>

        <Form.Item
          name="servingUnit"
          label="Serving Unit"
          rules={[{ required: true }]}
        >
          <Select placeholder="Select serving unit">
            <Select.Option value="g">g (grams)</Select.Option>
          </Select>
        </Form.Item>


        <Form.Item name="active" label="Active" valuePropName="checked">
          <Switch />
        </Form.Item>

        <Form.Item shouldUpdate={(prev, curr) => prev.active !== curr.active}>
          {() =>
            form.getFieldValue('active') && (
              <Form.Item
                name="tag"
                label="Tag"
                rules={[{ required: true, type: 'number', message: 'Tag is required when item is active' }]}
              >
                <InputNumber min={0} style={{ width: '100%' }} />
              </Form.Item>
            )
          }
        </Form.Item>

        <Divider orientation="left">Proximates</Divider>

        <Form.Item
          name={['proximates', 'unit']}
          label="Unit"
          rules={[{ required: true, message: 'Unit is required for proximates' }]}
        >
          <Select placeholder="Select unit">
            <Select.Option value="g">g (grams)</Select.Option>
          </Select>
        </Form.Item>

        {renderNutritionFields('proximates', [
          'water', 'energyGeneral', 'energySpecific', 'nitrogen', 'protein', 'totalLipid', 'ash'
        ], proximatesUnit || '')}

        <Divider orientation="left">Carbohydrates</Divider>
        <Form.Item
          name={['carbohydrates', 'unit']}
          label="Unit"
          rules={[{ required: true, message: 'Unit is required for carbohydrates' }]}
        >
          <Select placeholder="Select unit">
            <Select.Option value="g">g (grams)</Select.Option>
          </Select>
        </Form.Item>

        {renderNutritionFields('carbohydrates', [
          'carbohydrate', 'fiber', 'totalSugars', 'sucrose', 'glucose', 'fructose', 'maltose', 'lactose'
        ], carbohydratesUnit || '')}

        <Divider orientation="left">Minerals</Divider>
        <Form.Item
          name={['minerals', 'unit']}
          label="Unit"
          rules={[{ required: true, message: 'Unit is required for minerals' }]}
        >
          <Select placeholder="Select unit">
            <Select.Option value="mg">mg (milligrams)</Select.Option>
          </Select>
        </Form.Item>

        {renderNutritionFields('minerals', [
          'calcium', 'iron', 'magnesium', 'phosphorus', 'potassium', 'sodium', 'zinc', 'copper', 'manganese'
        ], mineralsUnit || '')}

        <Divider orientation="left">Vitamins</Divider>

        <Form.Item
          name={['vitamins', 'unit']}
          label="Unit"
          rules={[{ required: true, message: 'Unit is required for vitamins' }]}
        >
          <Select placeholder="Select unit">
            <Select.Option value="mg">mg (milligrams)</Select.Option>
          </Select>
        </Form.Item>

        {renderNutritionFields('vitamins', [
          'vitaminA', 'vitaminB1', 'vitaminB2', 'vitaminB3', 'vitaminB5', 'vitaminB6', 'vitaminB7',
          'vitaminB9', 'vitaminB12', 'vitaminC', 'vitaminD', 'vitaminE', 'vitaminK'
        ], vitaminsUnit || '')}

        <Form.Item>
          <div style={{ display: 'flex', justifyContent: 'flex-end'}}>
            <Button type="primary" htmlType="submit" loading={loading}>
              {initialValues ? 'Update' : 'Create'}
            </Button>
          </div>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default FoodForm;
