import React, { useState } from 'react';
import { Upload, Button, Image } from 'antd';
import { UploadOutlined } from '@ant-design/icons';

const ImageUploader: React.FC = () => {
  const [imageUrl, setImageUrl] = useState<string | null>(null);

  const handleImageChange = (file: File) => {
    const url = URL.createObjectURL(file);
    setImageUrl(url);
  };

  return (
    <div style={{ textAlign: 'center' }}>
      <Upload
        beforeUpload={(file) => {
          handleImageChange(file);
          return false;
        }}
        showUploadList={false}
        accept="image/*"
      >
        <Button icon={<UploadOutlined />}>Select Image</Button>
      </Upload>

      {imageUrl && (
        <div style={{ marginTop: 16 }}>
          <Image
            src={imageUrl}
            alt="Preview"
            style={{ maxWidth: '100%', maxHeight: 400 }}
          />
        </div>
      )}
    </div>
  );
};

export default ImageUploader;
