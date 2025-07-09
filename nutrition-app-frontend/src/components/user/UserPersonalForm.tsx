import React, { useEffect } from 'react';
import { Card, Button, Form, Input, Descriptions } from 'antd';
import dayjs from 'dayjs';
import type { UserDetails, UpdateProfileRequest } from '../../types/UserEntities';

interface UserPersonalFormProps {
  user: UserDetails;
  onClose: () => void;
  onSave: (data: UpdateProfileRequest) => void;
}

const UserPersonalForm: React.FC<UserPersonalFormProps> = ({ user, onClose, onSave }) => {
  const [form] = Form.useForm<UpdateProfileRequest>();

  useEffect(() => {
    if (user) {
      form.setFieldsValue({
        fullName: user.fullName,
        phoneNumber: user.phoneNumber,
        address: user.address,
        city: user.city,
        postalCode: user.postalCode,
        country: user.country
      });
    }
  }, [user, form]);

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      onSave(values);
    } catch (err) {
      console.error('Validation failed:', err);
    }
  };

  if (!user) return null;

  return (
    <Card title="Your Profile" extra={<Button onClick={onClose}>Close</Button>} style={{ maxWidth: 700, margin: '0 auto' }}>
      <Descriptions column={1} bordered>
        <Descriptions.Item label="Username">{user.username}</Descriptions.Item>
        <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
        <Descriptions.Item label="Role">{user.role}</Descriptions.Item>
        <Descriptions.Item label="Created At">{dayjs(user.createdAt).format('YYYY-MM-DD HH:mm')}</Descriptions.Item>
      </Descriptions>

      <Form layout="vertical" form={form} style={{ marginTop: 24 }} onFinish={handleSubmit}>
        <Form.Item label="Full Name" name="fullName" >
          <Input />
        </Form.Item>
        <Form.Item label="Phone Number" name="phoneNumber">
          <Input />
        </Form.Item>
        <Form.Item label="Address" name="address">
          <Input />
        </Form.Item>
        <Form.Item label="City" name="city">
          <Input />
        </Form.Item>
        <Form.Item label="Postal Code" name="postalCode">
          <Input />
        </Form.Item>
        <Form.Item label="Country" name="country">
          <Input />
        </Form.Item>

        <Button type="primary" htmlType="submit">Save Changes</Button>
      </Form>
    </Card>
  );
};

export default UserPersonalForm;
