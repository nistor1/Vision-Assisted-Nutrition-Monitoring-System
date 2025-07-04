import React, { useEffect, useState } from 'react';
import { Card, Form, Input, Button, Select, Divider, Space, InputNumber, message } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import type {
  CreateMealRequest,
  UpdateMealRequest,
  MealType
} from '../../types/MealEntities';
import type { FoodItemSimple } from '../../types/FoodEntities';
import { apiService } from '../../services/api';

interface MealFormProps {
  meal?: UpdateMealRequest;
  onSubmit: (values: CreateMealRequest | UpdateMealRequest) => Promise<void>;
  onCancel: () => void;
  onDelete?: () => void;
}

const { Option } = Select;
const MEAL_TYPES: MealType[] = ['BREAKFAST', 'LUNCH', 'DINNER', 'SNACK', 'OTHER'];

interface EntryFormValue {
  foodItemId: string;
  quantity: number;
}

interface MealFormValues {
  name: string;
  mealType: MealType;
  entries: EntryFormValue[];
}

const MealForm: React.FC<MealFormProps> = ({ meal, onSubmit, onCancel, onDelete }) => {
  const [form] = Form.useForm<MealFormValues>();
  const [foodOptions, setFoodOptions] = useState<FoodItemSimple[]>([]);

  useEffect(() => {
    apiService.getFoodItemsSimple({ page: 0, size: 100 })
      .then(response => {
        const items = response.body.payload?.content || [];
        setFoodOptions(items);
      })
      .catch(err => {
        console.error('Failed to load food items', err);
        message.error('Could not load food items');
      });
  }, []);

  useEffect(() => {
    if (meal) {
      form.setFieldsValue({
        name: meal.name,
        mealType: meal.mealType,
        entries: meal.entries.map(({ foodItemId, quantity }) => ({ foodItemId, quantity }))
      });
    }
  }, [meal, form]);

  const handleFinish = async (values: MealFormValues) => {
    const combined: Record<string, number> = {};
    values.entries.forEach(({ foodItemId, quantity }) => {
      combined[foodItemId] = (combined[foodItemId] || 0) + quantity;
    });

    const mergedEntries: EntryFormValue[] = Object.entries(combined).map(
      ([foodItemId, quantity]) => ({ foodItemId, quantity })
    );

    const payload: CreateMealRequest | UpdateMealRequest = {
      ...(meal && { id: meal.id }),
      name: values.name,
      mealType: values.mealType,
      entries: mergedEntries
    };

    try {
      await onSubmit(payload);
    } catch (err) {
      console.error('Submit error', err);
      message.error('Failed to save meal');
    }
  };

  const handleAddFoodItem = () => {
    const currentEntries: EntryFormValue[] = form.getFieldValue('entries') || [];

    const availableOptions = foodOptions.filter(opt =>
      !currentEntries.some(entry => entry.foodItemId === opt.id)
    );

    if (availableOptions.length === 0) {
      message.info("All food items are already added.");
      return;
    }

    const newItemId = availableOptions[0].id;

    const updatedEntries = currentEntries.map((entry) =>
      entry.foodItemId === newItemId
        ? { ...entry, quantity: entry.quantity + 1 }
        : entry
    );

    if (!currentEntries.find((entry) => entry.foodItemId === newItemId)) {
      updatedEntries.push({ foodItemId: newItemId, quantity: 1 });
    }

    form.setFieldsValue({ entries: updatedEntries });
  };

  return (
    <Card style={{ maxWidth: 1000, margin: '0 auto' }}>
      <Form form={form} layout="vertical" onFinish={handleFinish}>
        <Form.Item name="name" label="Meal Name" rules={[{ required: true }]}>
          <Input placeholder="Enter meal name" />
        </Form.Item>

        <Form.Item name="mealType" label="Meal Type" rules={[{ required: true }]}>
          <Select placeholder="Select meal type">
            {MEAL_TYPES.map(type => (
              <Option key={type} value={type}>{type}</Option>
            ))}
          </Select>
        </Form.Item>

        <Divider>Food Items</Divider>

        <Form.List name="entries">
          {(fields, { remove }) => (
            <>
              {fields.map(({ key, name, ...restField }) => (
                <Space key={key} style={{ display: 'flex', marginBottom: 8 }} align="start">
                  <Form.Item
                    {...restField}
                    name={[name, 'foodItemId']}
                    rules={[{ required: true, message: 'Select food item' }]}
                  >
                    <Select placeholder="Select food item" style={{ width: 200 }}>
                      {foodOptions.map(item => (
                        <Option
                          key={item.id}
                          value={item.id}
                          disabled={form.getFieldValue('entries')?.some((e: EntryFormValue, i: number) => e.foodItemId === item.id && i !== name)}
                        >
                          {item.foodName}
                        </Option>
                      ))}
                    </Select>
                  </Form.Item>

                  <Form.Item
                    {...restField}
                    name={[name, 'quantity']}
                    rules={[{ required: true, message: 'Enter quantity' }]}
                  >
                    <InputNumber min={0.1} step={0.1} placeholder="Qty" />
                  </Form.Item>

                  <MinusCircleOutlined onClick={() => remove(name)} />
                </Space>
              ))}

              <Form.Item>
                <Button type="dashed" onClick={handleAddFoodItem} block icon={<PlusOutlined />}>
                  Add Food Item
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>

        <Space style={{ display: 'flex', justifyContent: 'flex-end', marginTop: 24 }}>
          <Button onClick={onCancel}>Cancel</Button>
          {meal?.id && (
            <Button danger onClick={onDelete}>
              Delete Meal
            </Button>
          )}
          <Button type="primary" htmlType="submit">
            {meal ? 'Confirm' : 'Create Meal'}
          </Button>
        </Space>
      </Form>
    </Card>
  );
};

export default MealForm;
