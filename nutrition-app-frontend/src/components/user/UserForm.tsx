import React, { useEffect } from 'react';
import { Form, Input, Button, Card, Select } from 'antd';
import type { UserDetails } from '../../types/UserEntities.ts';

const { Option } = Select;

interface UserFormProps {
  initialValues?: Partial<UserDetails>;
  onSubmit: (values: UserDetails) => void;
  onCancel: () => void;
  loading?: boolean;
}

const UserForm: React.FC<UserFormProps> = ({ initialValues, onSubmit, onCancel, loading }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (initialValues) {
      form.setFieldsValue(initialValues);
    }
  }, [initialValues, form]);

  const handleFinish = (values: Partial<UserDetails>) => {
    const fullValues: UserDetails = {
      ...(initialValues ?? {}),
      ...values,
    } as UserDetails;

    onSubmit(fullValues);
  };

  return (
    <Card>
      <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
        <h2>General Info</h2>
        <Button onClick={onCancel} type="default">
          Cancel
        </Button>
      </div>

      <Form layout="vertical" form={form} onFinish={handleFinish}>
        <Form.Item name="username" label="Username" rules={[{required: true}]}>
          <Input/>
        </Form.Item>
        <Form.Item name="email" label="Email" rules={[{required: true, type: 'email'}]}>
          <Input/>
        </Form.Item>
        {!initialValues?.id && (
          <Form.Item name="password" label="Password" rules={[{required: true, min: 6}]}>
            <Input.Password/>
          </Form.Item>
        )}
        <Form.Item name="fullName" label="Full Name">
          <Input/>
        </Form.Item>
        <Form.Item name="phoneNumber" label="Phone Number">
          <Input/>
        </Form.Item>
        <Form.Item name="address" label="Address">
          <Input/>
        </Form.Item>
        <Form.Item name="city" label="City">
          <Input/>
        </Form.Item>
        <Form.Item name="postalCode" label="Postal Code">
          <Input/>
        </Form.Item>
        <Form.Item name="country" label="Country">
          <Input/>
        </Form.Item>
        <Form.Item name="role" label="Role" rules={[{required: true}]}>
          <Select placeholder="Select role">
            <Option value="USER">USER</Option>
            <Option value="ADMIN">ADMIN</Option>
          </Select>
        </Form.Item>
        <Form.Item>
          <div style={{display: 'flex', justifyContent: 'flex-end'}}>
            <Button type="primary" htmlType="submit" loading={loading}>
              {initialValues ? 'Update' : 'Create'}
            </Button>
          </div>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default UserForm;
